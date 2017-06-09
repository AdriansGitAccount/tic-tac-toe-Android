package mad.game.app.playerapp6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectPlayer2 extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";

    private  ListView listViewPlayers;
    private ArrayList<Player> allPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player2);

        listViewPlayers = (ListView) findViewById(R.id.ListViewSelectPlayer2);
        listViewPlayers.setOnItemClickListener(this);

        // fill players list
        //PlayerDB db = new PlayerDB(this);
        // allPlayers = db.getAllPlayers();
        updateDisplay();
    }


    public void updateDisplay() {

        // get the players from the database.
        PlayerDB playerDB = new PlayerDB(this);
        allPlayers = playerDB.getAllPlayers();

        // create a ArrayList of HashMap<String, String> objects for the simple adapter
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();

        int i =1;
        for(Player p: allPlayers){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("playerName", p.getName());
            //map.put("wins", String.valueOf(p.getWins()));
            //map.put("losses", String.valueOf(p.getLosses()));
            //map.put("ties", String.valueOf(p.getTies()));
            data.add(map);
            Log.d(" [Player "+i+"]", p.toString());
            i++;
        }

        // create the resource, from, and to variables
        int resource = R.layout.activity_select_player2_item;
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
        Player player2 = allPlayers.get(position);

        GameEmulator.setThePlayer2(player2);
        TicTacToe.setPlayer2(player2);
        // create an intent
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("name", player2.getName());
        intent.putExtra("wins", player2.getWins());
        intent.putExtra("losses", player2.getLosses());
        intent.putExtra("ties", player2.getTies());

        this.startActivity(intent);
        finish();
    }
}
