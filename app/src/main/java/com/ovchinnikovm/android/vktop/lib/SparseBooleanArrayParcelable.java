package com.ovchinnikovm.android.vktop.lib;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class SparseBooleanArrayParcelable extends SparseBooleanArray implements Parcelable {

    public static Parcelable.Creator<SparseBooleanArrayParcelable> CREATOR = new Parcelable.Creator<SparseBooleanArrayParcelable>() {
        @Override
        public SparseBooleanArrayParcelable createFromParcel(Parcel in) {
            return new SparseBooleanArrayParcelable(in);
        }

        @Override
        public SparseBooleanArrayParcelable[] newArray(int size) {
            return new SparseBooleanArrayParcelable[size];
        }
    };

    public SparseBooleanArrayParcelable(Parcel in) {
        boolean[] values = in.createBooleanArray();
        int[] keys = in.createIntArray();

        for (int i = 0; i < values.length; i++) {
            append(keys[i], values[i]);
        }
    }

    public SparseBooleanArrayParcelable(SparseBooleanArray sparseBooleanArray) {
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            this.put(sparseBooleanArray.keyAt(i), sparseBooleanArray.valueAt(i));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int[] keys = new int[size()];
        boolean[] values = new boolean[size()];

        for (int i = 0; i < size(); i++) {
            keys[i] = keyAt(i);
            values[i] = valueAt(i);
        }

        dest.writeIntArray(keys);
        dest.writeBooleanArray(values);
    }
}