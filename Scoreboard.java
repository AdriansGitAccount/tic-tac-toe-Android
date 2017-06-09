package mad.game.app.playerapp6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Scoreboard extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";

    private TextView textViewTitle;
    private TextView textViewPlayersName;
    private TextView textViewWins;
    private TextView textViewLosses;
    private TextView textViewTies;

    private ListView playersListView;

    private ArrayList<Player> allPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_listview);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);

//        textViewPlayersName = (TextView) findViewById(R.id.textViewPlayersName);
//        textViewWins = (TextView) findViewById(R.id.textViewPlayersWins);
//        textViewLosses = (TextView) findViewById(R.id.textViewPlayersLosses);
//        textViewTies = (TextView) findViewById(R.id.textViewPlayersTies);

        playersListView = (ListView) findViewById(R.id.scoreboard_ListView);
        playersListView.setOnItemClickListener(this);
//        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                // Alert the user
//                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
//                builder1.setMessage("Are you Sure you Want to Delete this Player! ???");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                PlayerDB playerDB = new PlayerDB(getApplicationContext());
//                                Player player = new Player();
//
//                                player.setPlayers(allPlayers);
//                                // get the player at the specified position
//                                Player thePlayer = player.getPlayer(position);
//                                int deletedPlayer = playerDB.deletePlayer(thePlayer.getId());
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//
//            }
//        });

        updateDisplay();
    }


    public void updateDisplay() {

        // set the title for the feed
        textViewTitle.setText("All Players");

        // get the players from the database.
        PlayerDB playerDB = new PlayerDB(this);
        allPlayers = playerDB.getAllPlayers();

        //new getPlayers().execute(allPlayers, null);

        // create a ArrayList of HashMap<String, String> objects for the simple adapter
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();

        int i =1;
        for(Player p: allPlayers){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("playerName", p.getName());
            map.put("wins", String.valueOf(p.getWins()));
            map.put("losses", String.valueOf(p.getLosses()));
            map.put("ties", String.valueOf(p.getTies()));
            data.add(map);
            Log.d(" ->[Player "+i+"]", p.toString());
            i++;
        }

        // create the resource, from, and to variables
        int resource = R.layout.activity_scoreboard_item;
        String[] from = {"playerName", "wins", "losses", "ties"};
        int[] to = {R.id.textViewPlayersName, R.id.textViewPlayersWins, R.id.textViewPlayersLosses, R.id.textViewPlayersTies};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);

        playersListView.setAdapter(adapter);
    }

    // inner class to get players from database using async task
    /*private class getPlayers extends AsyncTask<Object, Object, ArrayList<Player>> {

        @Override
        protected ArrayList<Player> doInBackground(Object... params) {

            PlayerDB playerDB = new PlayerDB(getApplicationContext());
            allPlayers = playerDB.getAllPlayers();

            if (allPlayers != null) {
                return allPlayers;
            } else {
                return null;
            }
        }
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        // Alert the user
//        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("Are you Sure you Want to Delete this Player! ???");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        PlayerDB playerDB = new PlayerDB(getApplicationContext());
//                        Player player = new Player();
//
//                        player.setPlayers(allPlayers);
//                        // get the player at the specified position
//                        Player thePlayer = player.getPlayer(position);
//                        int deletedPlayer = playerDB.deletePlayer(thePlayer.getId());
//                        dialog.cancel();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//        mainMenu(view);

    }

    public void mainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}
