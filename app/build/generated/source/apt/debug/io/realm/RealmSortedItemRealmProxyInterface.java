package io.realm;


public interface RealmSortedItemRealmProxyInterface {
    public Integer realmGet$sortId();
    public void realmSet$sortId(Integer value);
    public String realmGet$groupId();
    public void realmSet$groupId(String value);
    public Integer realmGet$userId();
    public void realmSet$userId(Integer value);
    public Integer realmGet$postsCount();
    public void realmSet$postsCount(Integer value);
    public String realmGet$groupIconUrl();
    public void realmSet$groupIconUrl(String value);
    public String realmGet$groupName();
    public void realmSet$groupName(String value);
    public String realmGet$sortRange();
    public void realmSet$sortRange(String value);
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byLikes();
    public void realmSet$byLikes(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value);
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byShares();
    public void realmSet$byShares(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value);
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byComments();
    public void realmSet$byComments(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value);
}
