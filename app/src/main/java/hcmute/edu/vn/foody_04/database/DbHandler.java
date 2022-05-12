package hcmute.edu.vn.foody_04.database;

import static java.lang.String.format;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.FoodSize;
import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.R;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "foody.sqlite";
    private static final Integer DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;
    private final Context context;

    private List<User> userList;
    private List<Food> foodList;
    private List<FoodSize> foodSizeList;
    private List<Order> orderList;
    private List<Restaurant> restaurantList;

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, DATABASE_FACTORY, DATABASE_VERSION);
        this.context = context;
    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        return c;
    }

    public byte[] convertDrawableToByteArray(Drawable drawable){
        // Convert khi đúng cấu trúc bitmap
        if (drawable instanceof BitmapDrawable) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        // Không cùng cấu trúc bitmap
        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        // Vẽ hình
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        // Chuyển kiểu
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void SampleData() {
        // region User
        userList = new ArrayList<>();
        userList.add(new User(1, "PNHD", "Male", "1-12-2001", "1234567890", "cat0", "123456"));
        userList.add(new User(2, "NDD", "Male", "1-1-2001", "1234567890", "ndd123", "1234567"));
        // endregion
        // region Food
        foodList = new ArrayList<>();
        // region Kem
        foodList.add(new Food(1, "Kem hộp sữa dừa", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_suadua, null)),
                "Kem tươi mát ngon lắm bạn ơi!", 1));
        foodList.add(new Food(1, "Kem ôc quế dâu", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem tươi mát ngon lắm bạn ơi!", 1));
        // endregion
        // region Banh mi
        foodList.add(new Food(1, "Hamburger bò", "Hamburger",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Bữa ăn đơn giản cho người đơn giản!", 1));
        foodList.add(new Food(1, "Hamburger heo", "Hamburger",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "Bữa ăn đơn giản cho người đơn giản!", 1));
        foodList.add(new Food(1, "Hamburger gà", "Hamburger",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_ga, null)),
                "Bữa ăn đơn giản cho người đơn giản!", 1));

        // endregion
        // region Banh ngot
        foodList.add(new Food(1, "Bánh matcha", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_matcha, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        foodList.add(new Food(1, "Bánh sầu riêng", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        foodList.add(new Food(1, "Bánh bông lan cuộn", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        foodList.add(new Food(1, "Bánh bông lan socola", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_socola, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        foodList.add(new Food(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        foodList.add(new Food(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 1));
        foodList.add(new Food(1, "Bánh su kem", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhsukem, null)),
                "Cảm nhận sự ngọt ngào của những chiếc bánh ngon!", 4));
        // endregion
        // region Com suon
        foodList.add(new Food(1, "Cơm sườn trứng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_trung, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 1));
        foodList.add(new Food(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 1));
        foodList.add(new Food(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 1));
        foodList.add(new Food(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 1));
        foodList.add(new Food(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 3));
        foodList.add(new Food(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 3));
        foodList.add(new Food(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 3));
        foodList.add(new Food(1, "Cơm sườn xào chua ngọt", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_chuangot, null)),
                "Món ăn bình dân truyền thống nhưng không thể thiếu hằng ngày!", 1));
        // endregion
        // region Mon nuoc

        foodList.add(new Food(1, "Hủ tiếu bò kho", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Hủ tiếu truyền thống kết hợp với sự cay nồng của món bò kho, bùng cháy vị giác!", 1));
        // endregion
        // region Tra sua
        foodList.add(new Food(1, "Trà sữa matcha", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_matcha, null)),
                "Uống đi, khum mặp đâu!", 2));
        foodList.add(new Food(1, "Trà sữa truyền thống", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_truyenthong, null)),
                "Uống đi, khum mặp đâu!", 2));
        foodList.add(new Food(1, "Trà sữa xoài", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_xoai, null)),
                "Uống đi, khum mặp đâu!", 2));
        // endregion

        // region foodSize
        foodSizeList = new ArrayList<>();
        Random random = new Random();
        for(int i = 1; i<= 45; i++){
            foodSizeList.add(new FoodSize(i, 1, (random.nextInt(20) + 1) * 1000d));
            foodSizeList.add(new FoodSize(i, 2, (random.nextInt(20) + 21) * 1000d));
            foodSizeList.add(new FoodSize(i, 3, (random.nextInt(20) + 41) * 1000d));
        }
        // endregion

        // region Order
        orderList = new ArrayList<>();
        orderList.add(new Order(1, 1, "Thủ Đức", "4/3/2022", 27000d, "Delivered"));
        orderList.add(new Order(2, 1, "Thủ Đức", "5/3/2022", 18000d, "Craft"));
        orderList.add(new Order(3, 3, "Quận 9", "4/3/2022", 68000d, "Coming"));
        // endregion

        // region Restaurant
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Grill & Cheer Vincom", "Vincom Plaza, Tầng 4, 50 Đ. Lê Văn Việt, Hiệp Phú, Quận 9, Thành phố Hồ Chí Minh", "7:00 - 23:00",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.res1, null))));
        restaurantList.add(new Restaurant(2, "Nhà Hàng Thiết Mộc Lan", "936 QL1A, Phường Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh", "8:00 - 21:00",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.res2, null))));
        restaurantList.add(new Restaurant(3, "Heo Mẹt Hải Sơn", "228a Đ. Man Thiện, Phường Tân Phú, Quận 9, Thành phố Hồ Chí Minh", "8:00 - 24:00",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.res3, null))));
        restaurantList.add(new Restaurant(4, "Nhà Hàng Năm IS", "Đường Đường Liên Phường, Phường Phú Hữu, Quận 9, Thành phố Hồ Chí Minh", "6:00 - 24:00",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.res4, null))));
        // endregion
    }

    public void addSampleData(SQLiteDatabase db) {
        SampleData();

        // Add restaurant
        for (Restaurant restaurant : restaurantList) {
            String sql = "INSERT INTO tblRestaurant VALUES(null, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, restaurant.getName());
            statement.bindString(2, restaurant.getAddress());
            statement.bindString(3, restaurant.getTime());
            statement.bindBlob(4, restaurant.getImage());
            statement.executeInsert();
        }

        // Add user
        for (User user : userList) {
            db.execSQL(format("INSERT INTO tblUser VALUES(null, '%s','%s', '%s', '%s', '%s', '%s')",
                    user.getName(), user.getGender(), user.getDateOfBirth(), user.getPhone(), user.getUsername(), user.getPassword()));
        }

        // Add food
        for (Food food : foodList) {
            String sql = "INSERT INTO tblFood VALUES (null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, food.getName());
            statement.bindString(2, food.getType());
            statement.bindBlob(3, food.getImage());
            statement.bindString(4, food.getDescription());
            statement.bindLong(5, food.getRestaurantId());
            statement.executeInsert();
        }

        // Add food size
        for (FoodSize foodSize : foodSizeList) {
            String sql = "INSERT INTO tblFoodSize VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, foodSize.getFoodId());
            statement.bindLong(2, foodSize.getSize());
            statement.bindDouble(3, foodSize.getPrice());
            statement.executeInsert();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create table "FoodSize"
        String queryCreateFoodSize = "CREATE TABLE IF NOT EXISTS tblFoodSize(" +
                "food_id INTEGER," +
                "size INTEGER ," +
                "price DOUBLE," +
                "PRIMARY KEY (food_id, size))";
        sqLiteDatabase.execSQL(queryCreateFoodSize);

        //Create table "Restaurant"
        String queryCreateRestaurant = "CREATE TABLE IF NOT EXISTS tblRestaurant(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200), " +
                "address NVARCHAR(200), "+
                "time NVARCHAR(200), "+
                "image BLOB)";
        sqLiteDatabase.execSQL(queryCreateRestaurant);

        //Create table "Food"
        String queryCreateFood = "CREATE TABLE IF NOT EXISTS tblFood(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "type NVARCHAR(200)," +
                "image BLOB," +
                "description NVARCHAR(200)," +
                "restaurant_id INTEGER)";
        sqLiteDatabase.execSQL(queryCreateFood);

        //Create table "User"
        String queryCreateUser = "CREATE TABLE IF NOT EXISTS tblUser(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "gender VARCHAR(10)," +
                "date_of_birth VARCHAR(20)," +
                "phone VARCHAR(15)," +
                "username VARCHAR(30)," +
                "password VARCHAR(100))";
        sqLiteDatabase.execSQL(queryCreateUser);

        //Create table "OrderDetail"
        String queryCreateOrderDetail = "CREATE TABLE IF NOT EXISTS tblOrderDetail(" +
                "order_id INTEGER," +
                "food_id INTEGER," +
                "size INTEGER," +
                "price DOUBLE," +
                "PRIMARY KEY (order_id, food_id, size))";
        sqLiteDatabase.execSQL(queryCreateOrderDetail);

        Log.i("SQLite", "DATABASE CREATED");
        addSampleData(sqLiteDatabase);
        Log.i("SQLite", "ADDED DATA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("SQLite","Upgrade SQLite");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFood");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSize");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrderDetail");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrder");


        onCreate(sqLiteDatabase);
    }

}
