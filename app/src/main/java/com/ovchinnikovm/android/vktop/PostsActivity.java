package com.ovchinnikovm.android.vktop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.entities.PostItem;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.squareup.leakcanary.RefWatcher;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;

public class PostsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView rvPosts;

    ArrayList<PostItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_container);
        downloadPosts();
    }

    public void downloadPosts() {
        VKRequest vkRequest = new VKApiGroups().getById(VKParameters.from("group_ids", "ru2ch"));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList vkList = (VKList) response.parsedModel;

                try {
                    VKRequest vkRequest1 = new VKApiWall()
                            .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + vkList.get(0).fields.getInt("id"),
                                    VKApiConst.COUNT, 100));
                    vkRequest1.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse vkResponse) {
                            super.onComplete(vkResponse);

                            Gson gson = new GsonBuilder().create();
                            PostsResponse postsResponse = gson
                                    .fromJson(vkResponse.responseString, PostsResponse.class);
                            for (PostItem postItem : postsResponse.response.items) {
                                PostItem item = new PostItem();
                                if (postItem.getText().equals(""))
                                    continue;
                                item.setText(postItem.getText());
                                mItems.add(item);
                            }
                            rvPosts.setAdapter(new PostsAdapter(mItems));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PostsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
