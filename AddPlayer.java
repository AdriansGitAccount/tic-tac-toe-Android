package mad.game.app.playerapp6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPlayer extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "mad.game.app.playerapp6.MESSAGE";
    private  static final String TAG = "PlayerApp version 6";

    private EditText editTextName;
    private Button buttonSavePlayer;

    private String playersName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);


        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonSavePlayer = (Button) findViewById(R.id.buttonAddPlayer);

        // Anonymous inner class - Key Listener for EditText
        editTextName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                    case KeyEvent.KEYCODE_DPAD_CENTER:

                        playersName = editTextName.getText().toString();

                        // hide the soft keyboard
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(
                                editTextName.getWindowToken(), 0);

                        // consume the event
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (view.getId() == R.id.buttonAddPlayer) {
                            playersName = editTextName.getText().toString();
                            Player player = insertNewPlayer(playersName);
                            if (player != null) {
                                Log.d(TAG , " New player INSERTED");
                            } else {
                                Log.d(TAG, " ERROR");
                            }
                        }
                        break;
                }
                // don't consume the event
                return false;
            }
        });
        buttonSavePlayer.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        PlayerDB db = new PlayerDB(this);
        Player lastPlayerAdded = db.getLastPlayerAdded();
        Log.d(TAG, lastPlayerAdded.toString());
        //int lastId = lastPlayerAdded.getId();

        switch (v.getId()) {
            case R.id.buttonAddPlayer:
                playersName = editTextName.getText().toString();
                if (!playersName.equals("")) {
                    //Player player = createNewPlayer(playersName, lastId);
                    Player player = insertNewPlayer(playersName);
                    Log.d(TAG, " --> New Player was INSERTED " + player.toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    String message = "Main Activity";
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                    finish();
                }
                break;


            default:
                // this.should_not_get_here():
                break;
        }
    }


//    private Player createNewPlayer(String name, int lastId) {
//        //lastId = lastId + 1;
//        Player player = new Player();
//        //player.setId(lastId);
//        player.setName(name);
//        player.setWin(0);
//        player.setLosses(0);
//        player.setTies(0);
//        return player;
//    }

    private Player insertNewPlayer(String name) {

        PlayerDB playerDb = new PlayerDB(this);

        Player player = new Player();
        //player.setId(lastId);
        player.setName(name);
        player.setWin(0);
        player.setLosses(0);
        player.setTies(0);

        long insertPlayerRowId = playerDb.insertPlayer(player);

        Log.d(TAG, "insertPlayerRowId " + insertPlayerRowId);

        Intent intent = new Intent(this, MainActivity.class);
        String message = "Main Activity";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        finish();
        return  player;
    }
}
