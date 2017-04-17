package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class RealmSortedItemRealmProxy extends com.ovchinnikovm.android.vktop.entities.RealmSortedItem
    implements RealmObjectProxy, RealmSortedItemRealmProxyInterface {

    static final class RealmSortedItemColumnInfo extends ColumnInfo {
        long sortIdIndex;
        long groupIdIndex;
        long userIdIndex;
        long postsCountIndex;
        long groupIconUrlIndex;
        long groupNameIndex;
        long sortRangeIndex;
        long byLikesIndex;
        long bySharesIndex;
        long byCommentsIndex;

        RealmSortedItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(10);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmSortedItem");
            this.sortIdIndex = addColumnDetails("sortId", objectSchemaInfo);
            this.groupIdIndex = addColumnDetails("groupId", objectSchemaInfo);
            this.userIdIndex = addColumnDetails("userId", objectSchemaInfo);
            this.postsCountIndex = addColumnDetails("postsCount", objectSchemaInfo);
            this.groupIconUrlIndex = addColumnDetails("groupIconUrl", objectSchemaInfo);
            this.groupNameIndex = addColumnDetails("groupName", objectSchemaInfo);
            this.sortRangeIndex = addColumnDetails("sortRange", objectSchemaInfo);
            this.byLikesIndex = addColumnDetails("byLikes", objectSchemaInfo);
            this.bySharesIndex = addColumnDetails("byShares", objectSchemaInfo);
            this.byCommentsIndex = addColumnDetails("byComments", objectSchemaInfo);
        }

        RealmSortedItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmSortedItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmSortedItemColumnInfo src = (RealmSortedItemColumnInfo) rawSrc;
            final RealmSortedItemColumnInfo dst = (RealmSortedItemColumnInfo) rawDst;
            dst.sortIdIndex = src.sortIdIndex;
            dst.groupIdIndex = src.groupIdIndex;
            dst.userIdIndex = src.userIdIndex;
            dst.postsCountIndex = src.postsCountIndex;
            dst.groupIconUrlIndex = src.groupIconUrlIndex;
            dst.groupNameIndex = src.groupNameIndex;
            dst.sortRangeIndex = src.sortRangeIndex;
            dst.byLikesIndex = src.byLikesIndex;
            dst.bySharesIndex = src.bySharesIndex;
            dst.byCommentsIndex = src.byCommentsIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(10);
        fieldNames.add("sortId");
        fieldNames.add("groupId");
        fieldNames.add("userId");
        fieldNames.add("postsCount");
        fieldNames.add("groupIconUrl");
        fieldNames.add("groupName");
        fieldNames.add("sortRange");
        fieldNames.add("byLikes");
        fieldNames.add("byShares");
        fieldNames.add("byComments");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private RealmSortedItemColumnInfo columnInfo;
    private ProxyState<com.ovchinnikovm.android.vktop.entities.RealmSortedItem> proxyState;
    private RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesRealmList;
    private RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesRealmList;
    private RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsRealmList;

    RealmSortedItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmSortedItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.ovchinnikovm.android.vktop.entities.RealmSortedItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$sortId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.sortIdIndex);
    }

    @Override
    public void realmSet$sortId(Integer value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'sortId' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$groupId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.groupIdIndex);
    }

    @Override
    public void realmSet$groupId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.groupIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.groupIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.groupIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.groupIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$userId() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.userIdIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$userId(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.userIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.userIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.userIdIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.userIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$postsCount() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.postsCountIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.postsCountIndex);
    }

    @Override
    public void realmSet$postsCount(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.postsCountIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.postsCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.postsCountIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.postsCountIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$groupIconUrl() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.groupIconUrlIndex);
    }

    @Override
    public void realmSet$groupIconUrl(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.groupIconUrlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.groupIconUrlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.groupIconUrlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.groupIconUrlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$groupName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.groupNameIndex);
    }

    @Override
    public void realmSet$groupName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.groupNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.groupNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.groupNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.groupNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sortRange() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sortRangeIndex);
    }

    @Override
    public void realmSet$sortRange(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sortRangeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sortRangeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sortRangeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sortRangeIndex, value);
    }

    @Override
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byLikes() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (byLikesRealmList != null) {
            return byLikesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.byLikesIndex);
            byLikesRealmList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, osList, proxyState.getRealm$realm());
            return byLikesRealmList;
        }
    }

    @Override
    public void realmSet$byLikes(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("byLikes")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> original = value;
                value = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.byLikesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byShares() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (bySharesRealmList != null) {
            return bySharesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.bySharesIndex);
            bySharesRealmList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, osList, proxyState.getRealm$realm());
            return bySharesRealmList;
        }
    }

    @Override
    public void realmSet$byShares(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("byShares")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> original = value;
                value = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.bySharesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> realmGet$byComments() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (byCommentsRealmList != null) {
            return byCommentsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.byCommentsIndex);
            byCommentsRealmList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, osList, proxyState.getRealm$realm());
            return byCommentsRealmList;
        }
    }

    @Override
    public void realmSet$byComments(RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("byComments")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> original = value;
                value = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.byCommentsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmSortedItem", 10, 0);
        builder.addPersistedProperty("sortId", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("groupId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("userId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("postsCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("groupIconUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("groupName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("sortRange", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("byLikes", RealmFieldType.LIST, "PostSortItem");
        builder.addPersistedLinkProperty("byShares", RealmFieldType.LIST, "PostSortItem");
        builder.addPersistedLinkProperty("byComments", RealmFieldType.LIST, "PostSortItem");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmSortedItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmSortedItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmSortedItem";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.ovchinnikovm.android.vktop.entities.RealmSortedItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        com.ovchinnikovm.android.vktop.entities.RealmSortedItem obj = null;
        if (update) {
            Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
            RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
            long pkColumnIndex = columnInfo.sortIdIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("sortId")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("sortId"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class), false, Collections.<String> emptyList());
                    obj = new io.realm.RealmSortedItemRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("byLikes")) {
                excludeFields.add("byLikes");
            }
            if (json.has("byShares")) {
                excludeFields.add("byShares");
            }
            if (json.has("byComments")) {
                excludeFields.add("byComments");
            }
            if (json.has("sortId")) {
                if (json.isNull("sortId")) {
                    obj = (io.realm.RealmSortedItemRealmProxy) realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.RealmSortedItemRealmProxy) realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class, json.getInt("sortId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'sortId'.");
            }
        }

        final RealmSortedItemRealmProxyInterface objProxy = (RealmSortedItemRealmProxyInterface) obj;
        if (json.has("groupId")) {
            if (json.isNull("groupId")) {
                objProxy.realmSet$groupId(null);
            } else {
                objProxy.realmSet$groupId((String) json.getString("groupId"));
            }
        }
        if (json.has("userId")) {
            if (json.isNull("userId")) {
                objProxy.realmSet$userId(null);
            } else {
                objProxy.realmSet$userId((int) json.getInt("userId"));
            }
        }
        if (json.has("postsCount")) {
            if (json.isNull("postsCount")) {
                objProxy.realmSet$postsCount(null);
            } else {
                objProxy.realmSet$postsCount((int) json.getInt("postsCount"));
            }
        }
        if (json.has("groupIconUrl")) {
            if (json.isNull("groupIconUrl")) {
                objProxy.realmSet$groupIconUrl(null);
            } else {
                objProxy.realmSet$groupIconUrl((String) json.getString("groupIconUrl"));
            }
        }
        if (json.has("groupName")) {
            if (json.isNull("groupName")) {
                objProxy.realmSet$groupName(null);
            } else {
                objProxy.realmSet$groupName((String) json.getString("groupName"));
            }
        }
        if (json.has("sortRange")) {
            if (json.isNull("sortRange")) {
                objProxy.realmSet$sortRange(null);
            } else {
                objProxy.realmSet$sortRange((String) json.getString("sortRange"));
            }
        }
        if (json.has("byLikes")) {
            if (json.isNull("byLikes")) {
                objProxy.realmSet$byLikes(null);
            } else {
                objProxy.realmGet$byLikes().clear();
                JSONArray array = json.getJSONArray("byLikes");
                for (int i = 0; i < array.length(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$byLikes().add(item);
                }
            }
        }
        if (json.has("byShares")) {
            if (json.isNull("byShares")) {
                objProxy.realmSet$byShares(null);
            } else {
                objProxy.realmGet$byShares().clear();
                JSONArray array = json.getJSONArray("byShares");
                for (int i = 0; i < array.length(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$byShares().add(item);
                }
            }
        }
        if (json.has("byComments")) {
            if (json.isNull("byComments")) {
                objProxy.realmSet$byComments(null);
            } else {
                objProxy.realmGet$byComments().clear();
                JSONArray array = json.getJSONArray("byComments");
                for (int i = 0; i < array.length(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$byComments().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.ovchinnikovm.android.vktop.entities.RealmSortedItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.ovchinnikovm.android.vktop.entities.RealmSortedItem obj = new com.ovchinnikovm.android.vktop.entities.RealmSortedItem();
        final RealmSortedItemRealmProxyInterface objProxy = (RealmSortedItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("sortId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sortId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sortId(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("groupId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$groupId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$groupId(null);
                }
            } else if (name.equals("userId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$userId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$userId(null);
                }
            } else if (name.equals("postsCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$postsCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$postsCount(null);
                }
            } else if (name.equals("groupIconUrl")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$groupIconUrl((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$groupIconUrl(null);
                }
            } else if (name.equals("groupName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$groupName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$groupName(null);
                }
            } else if (name.equals("sortRange")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sortRange((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sortRange(null);
                }
            } else if (name.equals("byLikes")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$byLikes(null);
                } else {
                    objProxy.realmSet$byLikes(new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$byLikes().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("byShares")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$byShares(null);
                } else {
                    objProxy.realmSet$byShares(new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$byShares().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("byComments")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$byComments(null);
                } else {
                    objProxy.realmSet$byComments(new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$byComments().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'sortId'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.ovchinnikovm.android.vktop.entities.RealmSortedItem copyOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.RealmSortedItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) cachedRealmObject;
        }

        com.ovchinnikovm.android.vktop.entities.RealmSortedItem realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
            RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
            long pkColumnIndex = columnInfo.sortIdIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.RealmSortedItemRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.ovchinnikovm.android.vktop.entities.RealmSortedItem copy(Realm realm, com.ovchinnikovm.android.vktop.entities.RealmSortedItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.ovchinnikovm.android.vktop.entities.RealmSortedItem realmObject = realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class, ((RealmSortedItemRealmProxyInterface) newObject).realmGet$sortId(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        RealmSortedItemRealmProxyInterface realmObjectSource = (RealmSortedItemRealmProxyInterface) newObject;
        RealmSortedItemRealmProxyInterface realmObjectCopy = (RealmSortedItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$groupId(realmObjectSource.realmGet$groupId());
        realmObjectCopy.realmSet$userId(realmObjectSource.realmGet$userId());
        realmObjectCopy.realmSet$postsCount(realmObjectSource.realmGet$postsCount());
        realmObjectCopy.realmSet$groupIconUrl(realmObjectSource.realmGet$groupIconUrl());
        realmObjectCopy.realmSet$groupName(realmObjectSource.realmGet$groupName());
        realmObjectCopy.realmSet$sortRange(realmObjectSource.realmGet$sortRange());

        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = realmObjectSource.realmGet$byLikes();
        if (byLikesList != null) {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesRealmList = realmObjectCopy.realmGet$byLikes();
            byLikesRealmList.clear();
            for (int i = 0; i < byLikesList.size(); i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem = byLikesList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyLikes = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byLikesItem);
                if (cachebyLikes != null) {
                    byLikesRealmList.add(cachebyLikes);
                } else {
                    byLikesRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, byLikesItem, update, cache));
                }
            }
        }


        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = realmObjectSource.realmGet$byShares();
        if (bySharesList != null) {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesRealmList = realmObjectCopy.realmGet$byShares();
            bySharesRealmList.clear();
            for (int i = 0; i < bySharesList.size(); i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem = bySharesList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyShares = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(bySharesItem);
                if (cachebyShares != null) {
                    bySharesRealmList.add(cachebyShares);
                } else {
                    bySharesRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, bySharesItem, update, cache));
                }
            }
        }


        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = realmObjectSource.realmGet$byComments();
        if (byCommentsList != null) {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsRealmList = realmObjectCopy.realmGet$byComments();
            byCommentsRealmList.clear();
            for (int i = 0; i < byCommentsList.size(); i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem = byCommentsList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyComments = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byCommentsItem);
                if (cachebyComments != null) {
                    byCommentsRealmList.add(cachebyComments);
                } else {
                    byCommentsRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, byCommentsItem, update, cache));
                }
            }
        }

        return realmObject;
    }

    public static long insert(Realm realm, com.ovchinnikovm.android.vktop.entities.RealmSortedItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long tableNativePtr = table.getNativePtr();
        RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long pkColumnIndex = columnInfo.sortIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$groupId = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupId();
        if (realmGet$groupId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
        }
        Number realmGet$userId = ((RealmSortedItemRealmProxyInterface) object).realmGet$userId();
        if (realmGet$userId != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId.longValue(), false);
        }
        Number realmGet$postsCount = ((RealmSortedItemRealmProxyInterface) object).realmGet$postsCount();
        if (realmGet$postsCount != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.postsCountIndex, rowIndex, realmGet$postsCount.longValue(), false);
        }
        String realmGet$groupIconUrl = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupIconUrl();
        if (realmGet$groupIconUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, realmGet$groupIconUrl, false);
        }
        String realmGet$groupName = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupName();
        if (realmGet$groupName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
        }
        String realmGet$sortRange = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortRange();
        if (realmGet$sortRange != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, realmGet$sortRange, false);
        }

        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byLikes();
        if (byLikesList != null) {
            OsList byLikesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byLikesIndex);
            for (com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem : byLikesList) {
                Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                if (cacheItemIndexbyLikes == null) {
                    cacheItemIndexbyLikes = PostSortItemRealmProxy.insert(realm, byLikesItem, cache);
                }
                byLikesOsList.addRow(cacheItemIndexbyLikes);
            }
        }

        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byShares();
        if (bySharesList != null) {
            OsList bySharesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.bySharesIndex);
            for (com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem : bySharesList) {
                Long cacheItemIndexbyShares = cache.get(bySharesItem);
                if (cacheItemIndexbyShares == null) {
                    cacheItemIndexbyShares = PostSortItemRealmProxy.insert(realm, bySharesItem, cache);
                }
                bySharesOsList.addRow(cacheItemIndexbyShares);
            }
        }

        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byComments();
        if (byCommentsList != null) {
            OsList byCommentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byCommentsIndex);
            for (com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem : byCommentsList) {
                Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                if (cacheItemIndexbyComments == null) {
                    cacheItemIndexbyComments = PostSortItemRealmProxy.insert(realm, byCommentsItem, cache);
                }
                byCommentsOsList.addRow(cacheItemIndexbyComments);
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long tableNativePtr = table.getNativePtr();
        RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long pkColumnIndex = columnInfo.sortIdIndex;
        com.ovchinnikovm.android.vktop.entities.RealmSortedItem object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$groupId = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupId();
            if (realmGet$groupId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
            }
            Number realmGet$userId = ((RealmSortedItemRealmProxyInterface) object).realmGet$userId();
            if (realmGet$userId != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId.longValue(), false);
            }
            Number realmGet$postsCount = ((RealmSortedItemRealmProxyInterface) object).realmGet$postsCount();
            if (realmGet$postsCount != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.postsCountIndex, rowIndex, realmGet$postsCount.longValue(), false);
            }
            String realmGet$groupIconUrl = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupIconUrl();
            if (realmGet$groupIconUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, realmGet$groupIconUrl, false);
            }
            String realmGet$groupName = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupName();
            if (realmGet$groupName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
            }
            String realmGet$sortRange = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortRange();
            if (realmGet$sortRange != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, realmGet$sortRange, false);
            }

            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byLikes();
            if (byLikesList != null) {
                OsList byLikesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byLikesIndex);
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem : byLikesList) {
                    Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                    if (cacheItemIndexbyLikes == null) {
                        cacheItemIndexbyLikes = PostSortItemRealmProxy.insert(realm, byLikesItem, cache);
                    }
                    byLikesOsList.addRow(cacheItemIndexbyLikes);
                }
            }

            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byShares();
            if (bySharesList != null) {
                OsList bySharesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.bySharesIndex);
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem : bySharesList) {
                    Long cacheItemIndexbyShares = cache.get(bySharesItem);
                    if (cacheItemIndexbyShares == null) {
                        cacheItemIndexbyShares = PostSortItemRealmProxy.insert(realm, bySharesItem, cache);
                    }
                    bySharesOsList.addRow(cacheItemIndexbyShares);
                }
            }

            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byComments();
            if (byCommentsList != null) {
                OsList byCommentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byCommentsIndex);
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem : byCommentsList) {
                    Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                    if (cacheItemIndexbyComments == null) {
                        cacheItemIndexbyComments = PostSortItemRealmProxy.insert(realm, byCommentsItem, cache);
                    }
                    byCommentsOsList.addRow(cacheItemIndexbyComments);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.RealmSortedItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long tableNativePtr = table.getNativePtr();
        RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long pkColumnIndex = columnInfo.sortIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
        }
        cache.put(object, rowIndex);
        String realmGet$groupId = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupId();
        if (realmGet$groupId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.groupIdIndex, rowIndex, false);
        }
        Number realmGet$userId = ((RealmSortedItemRealmProxyInterface) object).realmGet$userId();
        if (realmGet$userId != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.userIdIndex, rowIndex, false);
        }
        Number realmGet$postsCount = ((RealmSortedItemRealmProxyInterface) object).realmGet$postsCount();
        if (realmGet$postsCount != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.postsCountIndex, rowIndex, realmGet$postsCount.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.postsCountIndex, rowIndex, false);
        }
        String realmGet$groupIconUrl = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupIconUrl();
        if (realmGet$groupIconUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, realmGet$groupIconUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, false);
        }
        String realmGet$groupName = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupName();
        if (realmGet$groupName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.groupNameIndex, rowIndex, false);
        }
        String realmGet$sortRange = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortRange();
        if (realmGet$sortRange != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, realmGet$sortRange, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, false);
        }

        OsList byLikesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byLikesIndex);
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byLikes();
        if (byLikesList != null && byLikesList.size() == byLikesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = byLikesList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem = byLikesList.get(i);
                Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                if (cacheItemIndexbyLikes == null) {
                    cacheItemIndexbyLikes = PostSortItemRealmProxy.insertOrUpdate(realm, byLikesItem, cache);
                }
                byLikesOsList.setRow(i, cacheItemIndexbyLikes);
            }
        } else {
            byLikesOsList.removeAll();
            if (byLikesList != null) {
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem : byLikesList) {
                    Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                    if (cacheItemIndexbyLikes == null) {
                        cacheItemIndexbyLikes = PostSortItemRealmProxy.insertOrUpdate(realm, byLikesItem, cache);
                    }
                    byLikesOsList.addRow(cacheItemIndexbyLikes);
                }
            }
        }


        OsList bySharesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.bySharesIndex);
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byShares();
        if (bySharesList != null && bySharesList.size() == bySharesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = bySharesList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem = bySharesList.get(i);
                Long cacheItemIndexbyShares = cache.get(bySharesItem);
                if (cacheItemIndexbyShares == null) {
                    cacheItemIndexbyShares = PostSortItemRealmProxy.insertOrUpdate(realm, bySharesItem, cache);
                }
                bySharesOsList.setRow(i, cacheItemIndexbyShares);
            }
        } else {
            bySharesOsList.removeAll();
            if (bySharesList != null) {
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem : bySharesList) {
                    Long cacheItemIndexbyShares = cache.get(bySharesItem);
                    if (cacheItemIndexbyShares == null) {
                        cacheItemIndexbyShares = PostSortItemRealmProxy.insertOrUpdate(realm, bySharesItem, cache);
                    }
                    bySharesOsList.addRow(cacheItemIndexbyShares);
                }
            }
        }


        OsList byCommentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byCommentsIndex);
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byComments();
        if (byCommentsList != null && byCommentsList.size() == byCommentsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = byCommentsList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem = byCommentsList.get(i);
                Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                if (cacheItemIndexbyComments == null) {
                    cacheItemIndexbyComments = PostSortItemRealmProxy.insertOrUpdate(realm, byCommentsItem, cache);
                }
                byCommentsOsList.setRow(i, cacheItemIndexbyComments);
            }
        } else {
            byCommentsOsList.removeAll();
            if (byCommentsList != null) {
                for (com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem : byCommentsList) {
                    Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                    if (cacheItemIndexbyComments == null) {
                        cacheItemIndexbyComments = PostSortItemRealmProxy.insertOrUpdate(realm, byCommentsItem, cache);
                    }
                    byCommentsOsList.addRow(cacheItemIndexbyComments);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long tableNativePtr = table.getNativePtr();
        RealmSortedItemColumnInfo columnInfo = (RealmSortedItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        long pkColumnIndex = columnInfo.sortIdIndex;
        com.ovchinnikovm.android.vktop.entities.RealmSortedItem object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((RealmSortedItemRealmProxyInterface) object).realmGet$sortId());
            }
            cache.put(object, rowIndex);
            String realmGet$groupId = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupId();
            if (realmGet$groupId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.groupIdIndex, rowIndex, false);
            }
            Number realmGet$userId = ((RealmSortedItemRealmProxyInterface) object).realmGet$userId();
            if (realmGet$userId != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.userIdIndex, rowIndex, false);
            }
            Number realmGet$postsCount = ((RealmSortedItemRealmProxyInterface) object).realmGet$postsCount();
            if (realmGet$postsCount != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.postsCountIndex, rowIndex, realmGet$postsCount.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.postsCountIndex, rowIndex, false);
            }
            String realmGet$groupIconUrl = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupIconUrl();
            if (realmGet$groupIconUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, realmGet$groupIconUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.groupIconUrlIndex, rowIndex, false);
            }
            String realmGet$groupName = ((RealmSortedItemRealmProxyInterface) object).realmGet$groupName();
            if (realmGet$groupName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.groupNameIndex, rowIndex, realmGet$groupName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.groupNameIndex, rowIndex, false);
            }
            String realmGet$sortRange = ((RealmSortedItemRealmProxyInterface) object).realmGet$sortRange();
            if (realmGet$sortRange != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, realmGet$sortRange, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.sortRangeIndex, rowIndex, false);
            }

            OsList byLikesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byLikesIndex);
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byLikes();
            if (byLikesList != null && byLikesList.size() == byLikesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = byLikesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem = byLikesList.get(i);
                    Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                    if (cacheItemIndexbyLikes == null) {
                        cacheItemIndexbyLikes = PostSortItemRealmProxy.insertOrUpdate(realm, byLikesItem, cache);
                    }
                    byLikesOsList.setRow(i, cacheItemIndexbyLikes);
                }
            } else {
                byLikesOsList.removeAll();
                if (byLikesList != null) {
                    for (com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem : byLikesList) {
                        Long cacheItemIndexbyLikes = cache.get(byLikesItem);
                        if (cacheItemIndexbyLikes == null) {
                            cacheItemIndexbyLikes = PostSortItemRealmProxy.insertOrUpdate(realm, byLikesItem, cache);
                        }
                        byLikesOsList.addRow(cacheItemIndexbyLikes);
                    }
                }
            }


            OsList bySharesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.bySharesIndex);
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byShares();
            if (bySharesList != null && bySharesList.size() == bySharesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = bySharesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem = bySharesList.get(i);
                    Long cacheItemIndexbyShares = cache.get(bySharesItem);
                    if (cacheItemIndexbyShares == null) {
                        cacheItemIndexbyShares = PostSortItemRealmProxy.insertOrUpdate(realm, bySharesItem, cache);
                    }
                    bySharesOsList.setRow(i, cacheItemIndexbyShares);
                }
            } else {
                bySharesOsList.removeAll();
                if (bySharesList != null) {
                    for (com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem : bySharesList) {
                        Long cacheItemIndexbyShares = cache.get(bySharesItem);
                        if (cacheItemIndexbyShares == null) {
                            cacheItemIndexbyShares = PostSortItemRealmProxy.insertOrUpdate(realm, bySharesItem, cache);
                        }
                        bySharesOsList.addRow(cacheItemIndexbyShares);
                    }
                }
            }


            OsList byCommentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.byCommentsIndex);
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = ((RealmSortedItemRealmProxyInterface) object).realmGet$byComments();
            if (byCommentsList != null && byCommentsList.size() == byCommentsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = byCommentsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem = byCommentsList.get(i);
                    Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                    if (cacheItemIndexbyComments == null) {
                        cacheItemIndexbyComments = PostSortItemRealmProxy.insertOrUpdate(realm, byCommentsItem, cache);
                    }
                    byCommentsOsList.setRow(i, cacheItemIndexbyComments);
                }
            } else {
                byCommentsOsList.removeAll();
                if (byCommentsList != null) {
                    for (com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem : byCommentsList) {
                        Long cacheItemIndexbyComments = cache.get(byCommentsItem);
                        if (cacheItemIndexbyComments == null) {
                            cacheItemIndexbyComments = PostSortItemRealmProxy.insertOrUpdate(realm, byCommentsItem, cache);
                        }
                        byCommentsOsList.addRow(cacheItemIndexbyComments);
                    }
                }
            }

        }
    }

    public static com.ovchinnikovm.android.vktop.entities.RealmSortedItem createDetachedCopy(com.ovchinnikovm.android.vktop.entities.RealmSortedItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.ovchinnikovm.android.vktop.entities.RealmSortedItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.ovchinnikovm.android.vktop.entities.RealmSortedItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) cachedObject.object;
            }
            unmanagedObject = (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        RealmSortedItemRealmProxyInterface unmanagedCopy = (RealmSortedItemRealmProxyInterface) unmanagedObject;
        RealmSortedItemRealmProxyInterface realmSource = (RealmSortedItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$sortId(realmSource.realmGet$sortId());
        unmanagedCopy.realmSet$groupId(realmSource.realmGet$groupId());
        unmanagedCopy.realmSet$userId(realmSource.realmGet$userId());
        unmanagedCopy.realmSet$postsCount(realmSource.realmGet$postsCount());
        unmanagedCopy.realmSet$groupIconUrl(realmSource.realmGet$groupIconUrl());
        unmanagedCopy.realmSet$groupName(realmSource.realmGet$groupName());
        unmanagedCopy.realmSet$sortRange(realmSource.realmGet$sortRange());

        // Deep copy of byLikes
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$byLikes(null);
        } else {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> managedbyLikesList = realmSource.realmGet$byLikes();
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> unmanagedbyLikesList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
            unmanagedCopy.realmSet$byLikes(unmanagedbyLikesList);
            int nextDepth = currentDepth + 1;
            int size = managedbyLikesList.size();
            for (int i = 0; i < size; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createDetachedCopy(managedbyLikesList.get(i), nextDepth, maxDepth, cache);
                unmanagedbyLikesList.add(item);
            }
        }

        // Deep copy of byShares
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$byShares(null);
        } else {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> managedbySharesList = realmSource.realmGet$byShares();
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> unmanagedbySharesList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
            unmanagedCopy.realmSet$byShares(unmanagedbySharesList);
            int nextDepth = currentDepth + 1;
            int size = managedbySharesList.size();
            for (int i = 0; i < size; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createDetachedCopy(managedbySharesList.get(i), nextDepth, maxDepth, cache);
                unmanagedbySharesList.add(item);
            }
        }

        // Deep copy of byComments
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$byComments(null);
        } else {
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> managedbyCommentsList = realmSource.realmGet$byComments();
            RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> unmanagedbyCommentsList = new RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem>();
            unmanagedCopy.realmSet$byComments(unmanagedbyCommentsList);
            int nextDepth = currentDepth + 1;
            int size = managedbyCommentsList.size();
            for (int i = 0; i < size; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem item = PostSortItemRealmProxy.createDetachedCopy(managedbyCommentsList.get(i), nextDepth, maxDepth, cache);
                unmanagedbyCommentsList.add(item);
            }
        }

        return unmanagedObject;
    }

    static com.ovchinnikovm.android.vktop.entities.RealmSortedItem update(Realm realm, com.ovchinnikovm.android.vktop.entities.RealmSortedItem realmObject, com.ovchinnikovm.android.vktop.entities.RealmSortedItem newObject, Map<RealmModel, RealmObjectProxy> cache) {
        RealmSortedItemRealmProxyInterface realmObjectTarget = (RealmSortedItemRealmProxyInterface) realmObject;
        RealmSortedItemRealmProxyInterface realmObjectSource = (RealmSortedItemRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$groupId(realmObjectSource.realmGet$groupId());
        realmObjectTarget.realmSet$userId(realmObjectSource.realmGet$userId());
        realmObjectTarget.realmSet$postsCount(realmObjectSource.realmGet$postsCount());
        realmObjectTarget.realmSet$groupIconUrl(realmObjectSource.realmGet$groupIconUrl());
        realmObjectTarget.realmSet$groupName(realmObjectSource.realmGet$groupName());
        realmObjectTarget.realmSet$sortRange(realmObjectSource.realmGet$sortRange());
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesList = realmObjectSource.realmGet$byLikes();
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byLikesRealmList = realmObjectTarget.realmGet$byLikes();
        if (byLikesList != null && byLikesList.size() == byLikesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = byLikesList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem = byLikesList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyLikes = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byLikesItem);
                if (cachebyLikes != null) {
                    byLikesRealmList.set(i, cachebyLikes);
                } else {
                    byLikesRealmList.set(i, PostSortItemRealmProxy.copyOrUpdate(realm, byLikesItem, true, cache));
                }
            }
        } else {
            byLikesRealmList.clear();
            if (byLikesList != null) {
                for (int i = 0; i < byLikesList.size(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem byLikesItem = byLikesList.get(i);
                    com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyLikes = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byLikesItem);
                    if (cachebyLikes != null) {
                        byLikesRealmList.add(cachebyLikes);
                    } else {
                        byLikesRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, byLikesItem, true, cache));
                    }
                }
            }
        }
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesList = realmObjectSource.realmGet$byShares();
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> bySharesRealmList = realmObjectTarget.realmGet$byShares();
        if (bySharesList != null && bySharesList.size() == bySharesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = bySharesList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem = bySharesList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyShares = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(bySharesItem);
                if (cachebyShares != null) {
                    bySharesRealmList.set(i, cachebyShares);
                } else {
                    bySharesRealmList.set(i, PostSortItemRealmProxy.copyOrUpdate(realm, bySharesItem, true, cache));
                }
            }
        } else {
            bySharesRealmList.clear();
            if (bySharesList != null) {
                for (int i = 0; i < bySharesList.size(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem bySharesItem = bySharesList.get(i);
                    com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyShares = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(bySharesItem);
                    if (cachebyShares != null) {
                        bySharesRealmList.add(cachebyShares);
                    } else {
                        bySharesRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, bySharesItem, true, cache));
                    }
                }
            }
        }
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsList = realmObjectSource.realmGet$byComments();
        RealmList<com.ovchinnikovm.android.vktop.entities.PostSortItem> byCommentsRealmList = realmObjectTarget.realmGet$byComments();
        if (byCommentsList != null && byCommentsList.size() == byCommentsRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = byCommentsList.size();
            for (int i = 0; i < objects; i++) {
                com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem = byCommentsList.get(i);
                com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyComments = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byCommentsItem);
                if (cachebyComments != null) {
                    byCommentsRealmList.set(i, cachebyComments);
                } else {
                    byCommentsRealmList.set(i, PostSortItemRealmProxy.copyOrUpdate(realm, byCommentsItem, true, cache));
                }
            }
        } else {
            byCommentsRealmList.clear();
            if (byCommentsList != null) {
                for (int i = 0; i < byCommentsList.size(); i++) {
                    com.ovchinnikovm.android.vktop.entities.PostSortItem byCommentsItem = byCommentsList.get(i);
                    com.ovchinnikovm.android.vktop.entities.PostSortItem cachebyComments = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cache.get(byCommentsItem);
                    if (cachebyComments != null) {
                        byCommentsRealmList.add(cachebyComments);
                    } else {
                        byCommentsRealmList.add(PostSortItemRealmProxy.copyOrUpdate(realm, byCommentsItem, true, cache));
                    }
                }
            }
        }
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmSortedItem = proxy[");
        stringBuilder.append("{sortId:");
        stringBuilder.append(realmGet$sortId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{groupId:");
        stringBuilder.append(realmGet$groupId() != null ? realmGet$groupId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(realmGet$userId() != null ? realmGet$userId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{postsCount:");
        stringBuilder.append(realmGet$postsCount() != null ? realmGet$postsCount() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{groupIconUrl:");
        stringBuilder.append(realmGet$groupIconUrl() != null ? realmGet$groupIconUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{groupName:");
        stringBuilder.append(realmGet$groupName() != null ? realmGet$groupName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sortRange:");
        stringBuilder.append(realmGet$sortRange() != null ? realmGet$sortRange() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{byLikes:");
        stringBuilder.append("RealmList<PostSortItem>[").append(realmGet$byLikes().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{byShares:");
        stringBuilder.append("RealmList<PostSortItem>[").append(realmGet$byShares().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{byComments:");
        stringBuilder.append("RealmList<PostSortItem>[").append(realmGet$byComments().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmSortedItemRealmProxy aRealmSortedItem = (RealmSortedItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aRealmSortedItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmSortedItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aRealmSortedItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
