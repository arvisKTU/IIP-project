package com.example.iip_projektas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class LeaderAdapter extends ArrayAdapter<Leader>
{
    public LeaderAdapter(Context context, List<Leader> objects) {
        super(context, R.layout.adapter_view_layout, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_view_layout,null);
        }

        TextView id = (TextView) v.findViewById(R.id.idView);
        TextView username = (TextView) v.findViewById(R.id.usernameView);
        TextView score = (TextView) v.findViewById(R.id.scoreView);

        Leader leader = getItem(position);

        id.setText(leader.getId());
        username.setText(leader.getUsername());
        score.setText(leader.getScore());

        return v;
    }
}
class Leader {
    String id;
    String username;
    String score;

    public Leader() {
    }

    public Leader(String id, String username, String score) {
        this.id = id;
        this.username = username;
        this.score=score;
    }
    public String getId()
    {
        return  id;
    }
    public void setId()
    {
        this.id=id;
    }
    public String getUsername()
    {
        return  username;
    }
    public void setUsername()
    {
        this.username=username;
    }
    public String getScore()
    {
        return  score;
    }
    public void setScore()
    {
        this.score=score;
    }
}

public class Leaderboard extends Activity {

    public LeaderAdapter adapter;
    public ListView mListView;
    LeaderboardDatabaseHandler dbhandler;
    List<Leader> leaderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        leaderList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        dbhandler = new LeaderboardDatabaseHandler(this);
        mListView = (ListView)findViewById(R.id.leader_list) ;

        //prepareContent();


        String[] from = new String[] { "name", "score"};
        int[] to = new int[] {R.id.listview_username, R.id.listview_score};
        viewAll();
       // deleteEntries();
    }


    public void viewAll()
    {
        int counter=1;
        Cursor res = dbhandler.getEntries();

        while(res.moveToNext()&& counter<=10)
        {
            Leader leader = new Leader(Integer.toString(counter),res.getString(1),res.getString(2));
            leaderList.add(leader);
            counter++;
        }
        adapter = new LeaderAdapter(this, leaderList);
        mListView.setAdapter(adapter);
    }

    public void showEntries(String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();
    }

    public void deleteEntries()
    {
        dbhandler.deleteEntries();
    }
}
