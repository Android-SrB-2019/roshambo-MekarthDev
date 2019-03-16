//Samuel OBrien

package nbcc.com.roshambo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void roshambo_click(View view) {
        Rochambo game = new Rochambo();
        String image_name = GetImageName(view);
        TextView result = findViewById(R.id.Result_TextView);

        PlayerMove(image_name, game, result);
        GameMove(game, result);

        result.setText(game.winLoseOrDraw());

        AnimateImage();
    }

    private void AnimateImage(){
        ImageView player_choice = findViewById(R.id.Player_Move_ImageView);
        ImageView game_choice = findViewById(R.id.Games_Move_ImageView);
        ObjectAnimator animatorPlayer = ObjectAnimator.ofFloat(player_choice,
                "rotationX", 0f, 360f)
                .setDuration(500);

        ObjectAnimator animatorGame = ObjectAnimator.ofFloat(game_choice,
                "rotationY", 0f, 360f)
                .setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorGame, animatorPlayer);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    private void GameMove(Rochambo game, TextView result){
        ImageView game_choice = findViewById(R.id.Games_Move_ImageView);

        if(game.getGameMove() == 0){
            game_choice.setImageResource(R.drawable.rock);
        }else if(game.getGameMove() == 1){
            game_choice.setImageResource(R.drawable.paper);
        }else if(game.getGameMove() == 2){
            game_choice.setImageResource(R.drawable.scissors);
        }else{
            result.setText(getString(R.string.set_error));
        }
    }

    private void PlayerMove(String image_name, Rochambo game, TextView result){
        ImageView player_choice = findViewById(R.id.Player_Move_ImageView);

        if(image_name.equals("rock.png")){
            player_choice.setImageResource(R.drawable.rock);
            game.playerMakesMove(0);
        }else if(image_name.equals("paper.png")){
            player_choice.setImageResource(R.drawable.paper);
            game.playerMakesMove(1);
        }else if(image_name.equals("scissors.png")){
            player_choice.setImageResource(R.drawable.scissors);
            game.playerMakesMove(2);
        }else{
            result.setText(R.string.set_error);
        }
    }

    private String GetImageName(View view){
        ImageView selected = (ImageView) view;
        String selected_item = getResources().getResourceEntryName(selected.getId());
        String[] string_parts = selected_item.split("_");
        String image_name = string_parts[0].toLowerCase() + ".png";
        return image_name;
    }
}
