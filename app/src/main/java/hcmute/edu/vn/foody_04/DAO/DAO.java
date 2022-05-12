package hcmute.edu.vn.foody_04.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.FoodSize;
import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.database.DbHandler;

public class DAO {

    DbHandler dbHelper;
    SQLiteDatabase db ;

    public DAO(Context context){
        dbHelper = new DbHandler(context);
        db = dbHelper.getReadableDatabase();
    }

    // region Restaurant
    public Restaurant getRestaurantInformation(Integer restaurantId){
        String query = "SELECT * FROM tblRestaurant WHERE id=" + restaurantId;
        Cursor cursor = dbHelper.getDataRow(query);
        return new Restaurant(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
    }
    // endregion

    // region Order
    public Integer quantityOfOrder(){
        String query = "SELECT COUNT(*) FROM tblOrder WHERE status='Delivered'";
        Cursor cursor = dbHelper.getDataRow(query);
        return cursor.getInt(0);
    }

    public void addOrder(Order order) {
        String query = "INSERT INTO tblOrder VALUES(null," +
                order.getUserId() + ",'" +
                order.getAddress() + "','" +
                order.getDateOfOrder() + "'," +
                order.getTotalValue() + ",'" +
                order.getStatus() + "')";
        dbHelper.queryData(query);
    }

    public void updateOrder(Order order){
        String query = "UPDATE tblOrder SET address='" + order.getAddress() +
                "', date_order='" + order.getDateOfOrder() +
                "', total_value=" + order.getTotalValue() +
                ", status='" + order.getStatus() +
                "' WHERE id=" + order.getId() +
                " AND user_id=" + order.getUserId();
        dbHelper.queryData(query);
    }

    public ArrayList<Order> getOrderOfUser(Integer userId, String status){
        ArrayList<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT * FROM tblOrder WHERE user_id=" + userId + ") WHERE status='" + status + "'";
        if(status.equals("Delivered")){
            query += " OR status='Canceled'";
        }
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()){
            orderList.add(new Order(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getString(5)));
        }
        return orderList;
    }
    // endregion

    // region User
    public void addUser(User user) {
        String query = "INSERT INTO tblUser VALUES(null,'" +
                user.getName() + "', '" +
                user.getGender() + "', '" +
                user.getDateOfBirth() + "', '" +
                user.getPhone() + "', '" +
                user.getUsername() + "', '" +
                user.getPassword() + "')";
        dbHelper.queryData(query);
    }

    public void updateUser(User user){
        String query = "UPDATE tblUser SET " +
                "name='" + user.getName() + "'," +
                "gender='" + user.getGender() + "'," +
                "date_of_birth='" + user.getDateOfBirth() + "'," +
                "phone='" + user.getPhone() + "'," +
                "password='" + user.getPassword() + "' " +
                "WHERE id=" + user.getId();
        dbHelper.queryData(query);
    }

    public Integer getNewestUserId(){
        String query = "SELECT * FROM tblUser";
        Cursor cursor = dbHelper.getData(query);
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public boolean UserExited(String username) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "'";
        Cursor cursor = dbHelper.getData(query);
        return cursor.moveToNext();
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = new User();
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor.getCount() > 0) {
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setGender(cursor.getString(2));
            user.setDateOfBirth(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            user.setUsername(cursor.getString(5));
            user.setPassword(cursor.getString(6));
            return user;
        }
        return null;
    }

    public boolean signIn(User user){
        User existedUser = getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        return existedUser != null;
    }
    // endregion

    // region Food
    public FoodSize getFoodDefaultSize(Integer foodId){
        String sql = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId;
        Cursor cursor = dbHelper.getDataRow(sql);
        if (cursor == null)
            return null;
        return new FoodSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2));
    }

    public FoodSize getFoodSize(Integer foodId, Integer size){
        String sql = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId + " AND size=" + size;
        Cursor cursor = dbHelper.getDataRow(sql);
        if (cursor == null)
            return null;
        return new FoodSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2));
    }

    public ArrayList<FoodSize> getAllFoodSize(Integer foodId){
        ArrayList<FoodSize> foodSizeList = new ArrayList<>();
        String sql = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId;
        Cursor cursor = dbHelper.getData(sql);
        while (cursor.moveToNext()){
            foodSizeList.add(new FoodSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2)));
        }
        return foodSizeList;
    }

    public Cursor getCart(Integer userId){
        return dbHelper.getDataRow("SELECT id FROM tblOrder WHERE status='Craft' AND user_id=" + userId);
    }

    public Food getFoodById(Integer id){
        String query = "SELECT * FROM tblFood WHERE id=" + id;
        Cursor cursor = dbHelper.getDataRow(query);
        return new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3), cursor.getString(4), cursor.getInt(5));
    }

    public ArrayList<Food> getFoodByKeyWord(String keyword){
        ArrayList<Food> listFood = new ArrayList<>();
        String query = "SELECT * FROM tblFood WHERE name LIKE '%" + keyword + "%'";
        Cursor cursor = dbHelper.getData(query);
        while(cursor.moveToNext()){
            listFood.add(new Food(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5))
            );
        }
        return listFood;
    }

    public ArrayList<Food> getFoodByType(String type){
        ArrayList<Food> listFood = new ArrayList<>();
        String query = "SELECT * FROM tblFood WHERE type='" + type + "'";
        try {
            Cursor cursor = dbHelper.getData(query);
            if(cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    listFood.add(new Food(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getBlob(3),
                            cursor.getString(4),
                            cursor.getInt(5))
                    );
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e)
        {

        }
        return listFood;
    }

    public ArrayList<Restaurant> getAllRestaurant(){
        ArrayList<Restaurant> listRestaurant = new ArrayList<>();
        String query = "SELECT * FROM tblRestaurant ";
        try {
            Cursor cursor = dbHelper.getData(query);
            if(cursor != null)
            {
                cursor.moveToFirst();
                while(cursor.moveToNext()){
                    listRestaurant.add(new Restaurant(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getBlob(4))
                    );
                }
            }
        }
        catch (Exception e)
        {

        }
        return listRestaurant;
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }

    public void deleteOrderDetailByOrderIdAndFoodId(Integer orderId, Integer foodId) {
        String query = "DELETE FROM tblOrderDetail WHERE food_id=" +
                foodId + " and order_id=" + orderId;
        dbHelper.queryData(query);
    }

    public ArrayList<OrderDetail> getCartDetailList(Integer orderId){
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblOrderDetail WHERE order_id=" + orderId;
        Cursor cursor = dbHelper.getData(query);
        while(cursor.moveToNext()){
            orderDetailArrayList.add(new OrderDetail(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getDouble(3)));
        }
        return orderDetailArrayList;
    }

    public void addOrderDetail(OrderDetail od) {
        String query = "INSERT INTO tblOrderDetail VALUES(" +
                od.getOrderId() + "," +
                od.getFoodId() + "," +
                od.getSize() + "," +
                od.getPrice() + ")";
        dbHelper.queryData(query);
    }
}
