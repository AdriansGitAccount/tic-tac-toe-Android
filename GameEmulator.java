package mad.game.app.playerapp6;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameEmulator extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";



    public static Player thePlayer1 = null;
    public static Player thePlayer2 = null;

    private Button player1WinsButton;
    private Button player2WinsButton;
    private Button playersTieButton;

    private TextView player1Name;
    private TextView player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_emulator);


        if( thePlayer1 != null && thePlayer2 != null){
            player1Name = (TextView) findViewById(R.id.textViewPlayer1);
            player2Name = (TextView) findViewById(R.id.textViewPlayer2);

            player1WinsButton = (Button) findViewById(R.id.buttonPlayer1Wins);
            player2WinsButton = (Button) findViewById(R.id.buttonPlayer2Wins);
            playersTieButton = (Button) findViewById(R.id.buttonTie);

            player1Name.setText(thePlayer1.getName());
            player2Name.setText(thePlayer2.getName());

            player1WinsButton.setText(thePlayer1.getName()+ " Wins");
            player2WinsButton.setText(thePlayer2.getName()+ " Wins");

            player1WinsButton.setOnClickListener(this);
            player2WinsButton.setOnClickListener(this);
            playersTieButton.setOnClickListener(this);
        } else {
            Intent intent = new Intent(this, SelectPlayer1.class);
            String message = "Select Player 1";
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        PlayerDB playerDB = new PlayerDB(this);
        Player player1 = playerDB.getPlayer(thePlayer1.getId());
        Player player2 = playerDB.getPlayer(thePlayer2.getId());

        switch (v.getId()) {
            case R.id.buttonPlayer1Wins:
                playerWins(player1);
                playerLooses(player2);
                viewScoreboard(v);
                break;

            case R.id.buttonPlayer2Wins:
                playerWins(player2);
                playerLooses(player1);
                viewScoreboard(v);
                break;

            case R.id.buttonTie:
                playersTie(player1, player2);
                viewScoreboard(v);
                break;

            default:
                break;
        }
    }

    public void playerWins( Player p) {
        PlayerDB playerDB = new PlayerDB(this);
        int win = p.getWins();
        win = win +1;
        p.setWin(win);
        // Save the win into the database.
        int isUpdated = playerDB.updatePlayer(p);
        Log.d(TAG, "--Updated -> "+isUpdated +"->"+p.toString());
    }
    public void playerLooses( Player p) {
        PlayerDB playerDB = new PlayerDB(this);
        int losses = p.getLosses();
        losses = losses +1;
        p.setLosses(losses);
        // Save the loss into the database.
        int isUpdated = playerDB.updatePlayer(p);
        Log.d(TAG, "--Updated -> "+isUpdated +"->"+p.toString());
    }
    public void playersTie(Player p1, Player p2) {
        PlayerDB playerDB = new PlayerDB(this);
        int p1Ties = p1.getTies();
        int p2Ties = p2.getTies();

        p1Ties = p1Ties +1;
        p2Ties= p2Ties +1;
        p1.setTies(p1Ties);
        p2.setTies(p2Ties);
        // Save the win and loss into the database.
        int isUpdated1 = playerDB.updatePlayer(p1);
        int isUpdated2 = playerDB.updatePlayer(p2);
        Log.d(TAG, "--Updated -> "+isUpdated1 +"->"+p1.toString());
        Log.d(TAG, "--Updated -> "+isUpdated2 +"->"+p2.toString());
    }

    public void viewScoreboard(View view){
        Intent intent = new Intent(this, Scoreboard.class);
        String message = "View ScoreBoard from Game Emulator";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public static Player getThePlayer1() {
        return thePlayer1;
    }

    public static void setThePlayer1(Player thePlayer1) {
        GameEmulator.thePlayer1 = thePlayer1;
    }

    public static Player getThePlayer2() {
        return thePlayer2;
    }

    public static void setThePlayer2(Player thePlayer2) {
        GameEmulator.thePlayer2 = thePlayer2;
    }

}
