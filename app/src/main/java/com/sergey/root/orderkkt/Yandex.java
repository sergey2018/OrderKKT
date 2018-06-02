package com.sergey.root.orderkkt;

import android.content.Context;

import com.sergey.root.orderkkt.Model.ListItem;
import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.ResourcesHandler;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.NetworkIOException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.json.Resource;

import java.io.IOException;
import java.util.ArrayList;

public class Yandex {
    private RestClient mClient;
    private String direct;
    private String dir;
    private Context mContext;

    public Yandex(Context context){
        Credentials credentials = new Credentials("",Preferes.getToken(context));
        mClient = new RestClient(credentials);
        dir = Preferes.getCuryer(context);
        mContext = context;
        Direct();
    }

    private void Direct(){
        direct = mContext.getResources().getString(R.string.app_name);
    }

    public ArrayList<ListItem> getItem(){
        final ArrayList<ListItem> items = new ArrayList<>();
        int offset = 0;
        int size = 0;
        Resource r;
        try {
            do{

                r = mClient.getResources(new ResourcesArgs.Builder().setPath("").setSort(ResourcesArgs.Sort.name).setOffset(offset).setParsingHandler(new ResourcesHandler() {
                    @Override
                    public void handleItem(Resource item) {
                        items.add(new ListItem(item));
                    }
                }).build());

                offset += 20;
                size = r.getResourceList().getItems().size();
                return items;
            }while (size>=20);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerIOException e) {
            e.printStackTrace();
        }
        return items;
    }
    public void createDirectory(){
        try {
            mClient.makeFolder(direct);
            mClient.makeFolder(direct+"/"+dir);
            mClient.makeFolder(direct+"/"+dir+"/Загрузка");
        } catch (ServerIOException e) {
            e.printStackTrace();
        } catch (NetworkIOException e) {
            e.printStackTrace();
        }
    }
}
