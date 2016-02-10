package michalchojnacki.magazynbmp.model;

import java.io.Serializable;

public class SparePart extends SparePartModel {

    private SparePart(Builder builder) {
        mNumber = builder.mNumber;
        mDescription = builder.mDescription;
        mType = builder.mType;
        mProducer = builder.mProducer;
        mLocation = builder.mLocation;
        mSupplier = builder.mSupplier;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getType() {
        return mType;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getProducer() {
        return mProducer;
    }

    public String getSupplier() {
        return mSupplier;
    }

    public static class Builder extends SparePartModel {

        public SparePart build() {
            return new SparePart(this);
        }

        public Builder number(String number) {
            mNumber = number;
            return this;
        }

        public Builder description(String description) {
            mDescription = description;
            return this;
        }

        public Builder type(String type) {
            mType = type;
            return this;
        }

        public Builder producer(String producer) {
            mProducer = producer;
            return this;
        }

        public Builder location(String location) {
            mLocation = location;
            return this;
        }

        public Builder supplier(String supplier) {
            mSupplier = supplier;
            return this;
        }
    }
}

class SparePartModel implements Serializable {

    String mNumber;
    String mDescription;
    String mType;
    String mProducer;
    String mLocation;
    String mSupplier;
}