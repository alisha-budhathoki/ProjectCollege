package com.alisha.roomfinderapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    public String id;
    public String name;
    public String desc;
    public String location;
    public String image;
    public String category_name;
    public long contact_no;
    public String price;
    public String perUnits;
    public String owner_name;
    public String owner_id;
    public int no_of_rooms;
    public boolean isFullFlat;
    public String ownerRules;
    public String district;
    public String exactLocation;
    public String date_added;
    public String updated_at;
    public String deleted_at;
    public String longitutde;
    public String lattitude;
    public int rating;
    public int viewCount;
    public String onlinePaymentType;
    public String services;


    public Room() {
    }

    protected Room(Parcel in) {
        id = in.readString();
        name = in.readString();
        desc = in.readString();
        location = in.readString();
        image = in.readString();
        category_name = in.readString();
        contact_no = in.readLong();
        price = in.readString();
        perUnits = in.readString();
        owner_name = in.readString();
        owner_id = in.readString();
        no_of_rooms = in.readInt();
        isFullFlat = in.readByte() != 0;
        ownerRules = in.readString();
        district = in.readString();
        exactLocation = in.readString();
        date_added = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        longitutde = in.readString();
        lattitude = in.readString();
        rating = in.readInt();
        viewCount = in.readInt();
        onlinePaymentType = in.readString();
        services = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(location);
        dest.writeString(image);
        dest.writeString(category_name);
        dest.writeLong(contact_no);
        dest.writeString(price);
        dest.writeString(perUnits);
        dest.writeString(owner_name);
        dest.writeString(owner_id);
        dest.writeInt(no_of_rooms);
        dest.writeByte((byte) (isFullFlat ? 1 : 0));
        dest.writeString(ownerRules);
        dest.writeString(district);
        dest.writeString(exactLocation);
        dest.writeString(date_added);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeString(longitutde);
        dest.writeString(lattitude);
        dest.writeInt(rating);
        dest.writeInt(viewCount);
        dest.writeString(onlinePaymentType);
        dest.writeString(services);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public long getContact_no() {
        return contact_no;
    }

    public void setContact_no(long contact_no) {
        this.contact_no = contact_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPerUnits() {
        return perUnits;
    }

    public void setPerUnits(String perUnits) {
        this.perUnits = perUnits;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public int getNo_of_rooms() {
        return no_of_rooms;
    }

    public void setNo_of_rooms(int no_of_rooms) {
        this.no_of_rooms = no_of_rooms;
    }

    public boolean isFullFlat() {
        return isFullFlat;
    }

    public void setFullFlat(boolean fullFlat) {
        isFullFlat = fullFlat;
    }

    public String getOwnerRules() {
        return ownerRules;
    }

    public void setOwnerRules(String ownerRules) {
        this.ownerRules = ownerRules;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getExactLocation() {
        return exactLocation;
    }

    public void setExactLocation(String exactLocation) {
        this.exactLocation = exactLocation;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getLongitutde() {
        return longitutde;
    }

    public void setLongitutde(String longitutde) {
        this.longitutde = longitutde;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getOnlinePaymentType() {
        return onlinePaymentType;
    }

    public void setOnlinePaymentType(String onlinePaymentType) {
        this.onlinePaymentType = onlinePaymentType;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
