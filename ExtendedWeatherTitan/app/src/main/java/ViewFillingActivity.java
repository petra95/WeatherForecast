import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by antalicsp on 2016. 08. 03..
 */
public class ViewFillingActivity extends Activity {
    private List<Mock> fillings;
    private Mock mock;

    @Override
    public void onResume() {
        super.onResume();
        mock = new Mock();
        fillings = new ArrayList<>();
        fillings.addAll(mock.generateArrayList());
    }

    public class Mock{
        private String first;
        private String second;

        Mock(){

        }

        Mock(String first, String second){
            this.first = first;
            this.second = second;
        }

        public String getSecond() {
            return second;
        }

        public String getFirst() {

            return first;
        }

        public ArrayList<Mock> generateArrayList(){
           ArrayList<Mock> list = new ArrayList<>();
           Random random = new Random();
           for(int i=0; i<random.nextInt(10)+1; i++){
               list.add(new Mock(Integer.toString(random.nextInt()),Integer.toString(random.nextInt())));
           }
           return list;
       }
    }

}
