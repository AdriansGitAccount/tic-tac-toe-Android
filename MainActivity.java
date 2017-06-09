package mad.game.app.playerapp6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";

    private Button buttonStartGame;
    private Button buttonViewScoreboard;
    private Button buttonSelectThePlayers;

    private Button buttonAddPlayer;
    private Button buttonTicTacToe;


    private Player player;

    // set up preferences
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonStartGame = (Button) findViewById(R.id.buttonStartGame);
        buttonViewScoreboard = (Button) findViewById(R.id.buttonViewScoreboard);
        buttonSelectThePlayers = (Button) findViewById(R.id.buttonSelectThePlayers);
        buttonAddPlayer = (Button) findViewById(R.id.buttonAddPlayer);
        buttonTicTacToe = (Button) findViewById(R.id.buttonTicTacToe);


        buttonStartGame.setOnClickListener(this);
        buttonViewScoreboard.setOnClickListener(this);
        buttonSelectThePlayers.setOnClickListener(this);
        buttonAddPlayer.setOnClickListener(this);
        buttonTicTacToe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonStartGame:
                // IF the two payers are not assigned direct the player to the SelectPlayers activity.
                startGame();
                break;

            case R.id.buttonViewScoreboard:
                viewScoreboard();
                break;

            case R.id.buttonSelectThePlayers:
                selectThePlayers();
                break;

            case R.id.buttonAddPlayer:
                addPlayer();
                break;

            case R.id.buttonTicTacToe:
                ticTacToe();
                break;

            default:
                // this.should_not_get_here():
                break;
        }
    }



    /** Called when the user clicks the startGame button */
    public void startGame(){
        Intent intent = new Intent(this, GameEmulator.class);
        //String message = "Start Game";
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }
    /** Called when the user clicks the viewScoreboard button */
    public void viewScoreboard(){
        Intent intent = new Intent(this, Scoreboard.class);
//        String message = "View ScoreBoard";
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }

    /** Called when the user clicks the selectPlayer1 button */
    public void selectThePlayers(){
        Intent intent = new Intent(this, SelectPlayer1.class);
//        String message = "Select Player 1";
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }
    /** Called when the user clicks the selectPlayer2 button */
    public void selectPlayer2(){
        Intent intent = new Intent(this, SelectPlayer2.class);
//        String message = "Select Player 2";
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }
    /** Called when the user clicks the addPlayer button */
    public void addPlayer(){
        Intent intent = new Intent(this, AddPlayer.class);
//        String message = "Add Player";
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
    }
    /** Called when the user clicks the ticTacToe button */
    private void ticTacToe() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // DO YOUR STUFFS HERE
                Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
//                String message = "Two Players Playing Tic Tac Toe";
//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                finish();
            }
        }).start();


    }
    /** Called when the user clicks the viewAllPlayers button
    public void viewAllPlayers(View view){
        Intent intent = new Intent(this, ViewAllPlayers.class);
        String message = "View All Players";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/


    @Override
    protected void onPause() {
        // save the instance variables
        //SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("playersName", player.getName());
//        editor.putInt("playersWins", player.getWins());
//        editor.putInt("playersLosses", player.getLosses());
//        editor.putInt("playersTies", player.getTies());
        //editor.commit();

        // do stuff before super.onPause()
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // do stuff after super.onResume()

        PlayerDB playerDB = new PlayerDB(this);
        final ArrayList<Player> allPlayers = playerDB.getAllPlayers();
        int i =1;
        for(Player p: allPlayers){
            Log.d(TAG, "-->[Player "+i+"] "+p.toString());
            i++;
        }
    }


    /*
    not implemented

    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.exit){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

