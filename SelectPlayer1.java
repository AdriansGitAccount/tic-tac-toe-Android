package mad.game.app.playerapp6;

import android.content.DialogInterface;
import android.content.Intent;
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

public class SelectPlayer1 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";

    //private Player player;
    private ArrayList<Player> allPlayers;

    private ListView listViewPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player1);

        listViewPlayers = (ListView) findViewById(R.id.ListViewSelectPlayer1);

        listViewPlayers.setOnItemClickListener(this);


        updateDisplay();
    }


    public void updateDisplay() {

        // get the players from the database.
        PlayerDB playerDB = new PlayerDB(this);
        allPlayers = playerDB.getAllPlayers();

        // create a ArrayList of HashMap<String, String> objects for the simple adapter
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();

        int i = 1;
        for (Player p : allPlayers) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("playerName", p.getName());
            map.put("wins", String.valueOf(p.getWins()));
            map.put("losses", String.valueOf(p.getLosses()));
            map.put("ties", String.valueOf(p.getTies()));
            data.add(map);
            Log.d(" [Player " + i + "] ", p.toString());
            i++;
        }

        // create the resource, from, and to vars for the adapter
        int resource = R.layout.activity_select_player1_item;
        //String[] from = {"playerName", "wins", "losses", "ties"};
        String[] from = {"playerName"};
        int[] to = {R.id.textViewSelectPlayersName};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);

        listViewPlayers.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // get the player1 at the specified position
        Player player1 = allPlayers.get(position);

        GameEmulator.setThePlayer1(player1);
        TicTacToe.setPlayer1(player1);

        // create an intent
        Intent intent = new Intent(this, SelectPlayer2.class);

        intent.putExtra("name", player1.getName());
        intent.putExtra("wins", player1.getWins());
        intent.putExtra("losses", player1.getLosses());
        intent.putExtra("ties", player1.getTies());

        this.startActivity(intent);
        finish();
    }
}
