package us.eiyou.express;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import us.eiyou.express.model.Express_info;

public class ExpressInfoActivity extends AppCompatActivity {

    BmobUser bmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_info);
        initView();
    }

    private void initView() {
        bmobUser = BmobUser.getCurrentUser(getApplicationContext());
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tl);
        tableLayout.setStretchAllColumns(true);
        BmobQuery<Express_info> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user", bmobUser.getUsername());
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(getApplicationContext(), new FindListener<Express_info>() {
            @Override
            public void onSuccess(List<Express_info> list) {
                Toast.makeText(getApplicationContext(),list.size()+"",Toast.LENGTH_LONG).show();
                for (int row = 0; row < list.size(); row++) {
                    TableRow tableRow = new TableRow(getApplicationContext());

                    Express_info express_info= list.get(row);
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(express_info.getCreatedAt().substring(2,10));
                    tableRow.addView(textView);
                    TextView textView1 = new TextView(getApplicationContext());
                    textView1.setText(express_info.getPhone());
                    tableRow.addView(textView1);
                    TextView textView2 = new TextView(getApplicationContext());
                    textView2.setText(express_info.getNumber());
                    tableRow.addView(textView2);
                    TextView textView3 = new TextView(getApplicationContext());
                    textView3.setText(express_info.getCode());
                    tableRow.addView(textView3);
                    TextView textView4 = new TextView(getApplicationContext());
                    textView4.setText(express_info.getExpress());
                    tableRow.addView(textView4);
                    TextView textView5 = new TextView(getApplicationContext());
                    textView5.setText(express_info.getLoc());
                    tableRow.addView(textView5);

                    tableLayout.addView(tableRow);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getApplicationContext(), "查询失败"+s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
