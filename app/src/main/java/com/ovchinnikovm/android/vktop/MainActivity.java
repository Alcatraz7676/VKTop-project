package com.ovchinnikovm.android.vktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.model.PostItem;
import com.ovchinnikovm.android.vktop.model.PostsResponse;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;

    ArrayList<PostItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        if (VKSdk.isLoggedIn()) {
            downloadPosts();
        } else {
            VKSdk.login(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {

            }
            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                            for (PostsResponse.Response.Post post : postsResponse.response.items) {
                                PostItem item = new PostItem();
                                if (post.text.equals(""))
                                    continue;
                                item.setText(post.text);
                                mItems.add(item);
                            }
                            rvPosts.setAdapter(new PostsAdapter(mItems));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
