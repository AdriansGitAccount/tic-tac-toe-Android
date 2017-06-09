package mad.game.app.playerapp6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by adrian on 07/02/2017.
 */

public class PlayerDB {

    // database constants
    public static final String DB_NAME = "game_emulator.db";
    public static final int DB_VERSION = 1;

    // Tip table constants
    public static final String PLAYERS_TABLE = "players";

    public static final String PLAYER_ID = "_id";
    public static final int PLAYER_ID_COL = 0;

//    public static final String THE_PLAYER_ID = "playerId";
//    public static final int THE_PLAYER_ID_COL = 1;

    public static final String PLAYER_NAME = "name";
    public static final int PLAYER_NAME_COL = 1;

    public static final String PLAYERS_WINS = "wins";
    public static final int PLAYERS_WINS_COL = 2;

    public static final String PLAYERS_LOSSES = "losses";
    public static final int PLAYERS_LOSSES_COL = 3;

    public static final String PLAYERS_TIES = "ties";
    public static final int PLAYERS_TIES_COL = 4;

    private static final String TAG = "PlayerDB class";

    // CREATE and DROP TABLE statements
    public static final String CREATE_PLAYERS_TABLE =
            // [IF NOT EXISTS]
            "CREATE TABLE IF NOT EXISTS " + PLAYERS_TABLE + " (" +
                    PLAYER_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    //THE_PLAYER_ID   + " INTEGER NOT NULL, " +
                    PLAYER_NAME     + " TEXT NOT NULL, " +
                    PLAYERS_WINS    + " INTEGER, " +
                    PLAYERS_LOSSES  + " INTEGER, " +
                    PLAYERS_TIES    + " INTEGER);";

    public static final String DROP_PLAYERS_TABLE =
            "DROP TABLE IF EXISTS " + PLAYERS_TABLE;

    // Constructor
    public PlayerDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_PLAYERS_TABLE);
            // insert default player
            db.execSQL("INSERT INTO "+PLAYERS_TABLE+" VALUES (1, 'adrian', 0, 0, 0)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Players table", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(PlayerDB.DROP_PLAYERS_TABLE);
            onCreate(db);
        }
    }

    // Database and Database Helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null) {
            db.close();
        }
    }

    // public methods
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> playersList = new ArrayList<>();
        openReadableDB();
        Cursor cursor = db.query(PLAYERS_TABLE, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Player player = new Player();
            player.setId(cursor.getInt(PLAYER_ID_COL));
            player.setName(cursor.getString(PLAYER_NAME_COL));
            player.setWin(cursor.getInt(PLAYERS_WINS_COL));
            player.setLosses(cursor.getInt(PLAYERS_LOSSES_COL));
            player.setTies(cursor.getInt(PLAYERS_TIES_COL));

            playersList.add(player);
        }
        if (cursor != null) {
            cursor.close();
        }
        closeDB();
        return playersList;
    }

    public Player getPlayer(int id) {
        String where = PLAYER_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        this.openReadableDB();
        Cursor cursor = db.query(PLAYERS_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Player player = getPlayerFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return player;
    }

    private static Player getPlayerFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Player player = new Player(
                    cursor.getInt(PLAYER_ID_COL),
                    cursor.getString(PLAYER_NAME_COL),
                    cursor.getInt(PLAYERS_WINS_COL),
                    cursor.getInt(PLAYERS_LOSSES_COL),
                    cursor.getInt(PLAYERS_TIES_COL));
                return player;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public long insertPlayer(Player player) {
        ContentValues cv = new ContentValues();

        //cv.put(PLAYER_ID, player.getId());
        cv.put(PLAYER_NAME, player.getName());
        cv.put(PLAYERS_WINS, player.getWins());
        cv.put(PLAYERS_LOSSES, player.getLosses());
        cv.put(PLAYERS_TIES, player.getTies());

        this.openWritableDB();
        long rowID = db.insert(PLAYERS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updatePlayer(Player player) {
        ContentValues cv = new ContentValues();

        cv.put(PLAYER_ID, player.getId());
        cv.put(PLAYER_NAME, player.getName());
        cv.put(PLAYERS_WINS, player.getWins());
        cv.put(PLAYERS_LOSSES, player.getLosses());
        cv.put(PLAYERS_TIES, player.getTies());

        String where = PLAYER_ID + "= ?";
        String[] whereArgs = { String.valueOf(player.getId()) };
//        String where = PLAYER_NAME + "= ?";
//        String[] whereArgs = {player.getName()};

        this.openWritableDB();
        int rowCount = db.update(PLAYERS_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deletePlayer(long id) {
        String where = PLAYER_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWritableDB();
        int rowCount = db.delete(PLAYERS_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public Player getLastPlayerAdded() {
        try {
            openReadableDB();

            Cursor cursor = db.query(PLAYERS_TABLE, null, null, null, null, null, null);
            cursor.moveToLast();

            Player player = new Player();
            player.setId(cursor.getInt(PLAYER_ID_COL));
            player.setName(cursor.getString(PLAYER_NAME_COL));
            player.setWin(cursor.getInt(PLAYERS_WINS_COL));
            player.setLosses(cursor.getInt(PLAYERS_LOSSES_COL));
            player.setTies(cursor.getInt(PLAYERS_TIES_COL));

            if (cursor != null) {
                cursor.close();
            }
            closeDB();

            return player;

        } catch (Exception ex) {
            Log.d(TAG, "Exception: " + ex);
        }
        return null;
    }


}

