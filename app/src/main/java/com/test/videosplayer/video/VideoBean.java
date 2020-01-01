package com.test.videosplayer.video;

public class VideoBean {

    private String default_sort_order;
    private String album;
    private String artist;
    private int bookMark;
    private String bucket_display_name;
    private String bucket_id;
    private String category;
    private int date_taken;
    private String description;
    private int duration;
    private int is_private;
    private String language;
    private double latitude;
    private double longitude;
    private int mini_thumb_magic;
    private String resolution;
    private String tags;
    private String data;
    private int date_added;
    private int date_modified;
    private String display_name;
    private int height;
    private String mime_type;
    private int size;
    private String title;
    private int width;
    private int id;

    public VideoBean(int id, String album, String display_name, int duration, String data, int size) {
        this.id = id;
        this.album = album;
        this.display_name = display_name;
        this.duration = duration;
        this.data = data;
        this.size = size;
    }

    public VideoBean(int id, String default_sort_order, String album, String artist, int bookMark,
                     String bucket_display_name, String bucket_id, String category, int date_taken,
                     String description, int duration, int is_private, String language,
                     double latitude, double longitude, int mini_thumb_magic, String resolution,
                     String tags, String data, int date_added, int date_modified,
                     String display_name, int height, String mime_type, int size, String title,
                     int width) {
        this.default_sort_order = default_sort_order;
        this.album = album;
        this.artist = artist;
        this.bookMark = bookMark;
        this.bucket_display_name = bucket_display_name;
        this.bucket_id = bucket_id;
        this.category = category;
        this.date_taken = date_taken;
        this.description = description;
        this.duration = duration;
        this.is_private = is_private;
        this.language = language;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mini_thumb_magic = mini_thumb_magic;
        this.resolution = resolution;
        this.tags = tags;
        this.data = data;
        this.date_added = date_added;
        this.date_modified = date_modified;
        this.display_name = display_name;
        this.height = height;
        this.mime_type = mime_type;
        this.size = size;
        this.title = title;
        this.width = width;
        this.id = id;
    }

    public String getDefault_sort_order() {
        return default_sort_order;
    }

    public void setDefault_sort_order(String default_sort_order) {
        this.default_sort_order = default_sort_order;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getBookMark() {
        return bookMark;
    }

    public void setBookMark(int bookMark) {
        this.bookMark = bookMark;
    }

    public String getBucket_display_name() {
        return bucket_display_name;
    }

    public void setBucket_display_name(String bucket_display_name) {
        this.bucket_display_name = bucket_display_name;
    }

    public String getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(String bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(int date_taken) {
        this.date_taken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getMini_thumb_magic() {
        return mini_thumb_magic;
    }

    public void setMini_thumb_magic(int mini_thumb_magic) {
        this.mini_thumb_magic = mini_thumb_magic;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDate_added() {
        return date_added;
    }

    public void setDate_added(int date_added) {
        this.date_added = date_added;
    }

    public int getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(int date_modified) {
        this.date_modified = date_modified;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "default_sort_order='" + default_sort_order + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", bookMark=" + bookMark +
                ", bucket_display_name='" + bucket_display_name + '\'' +
                ", bucket_id='" + bucket_id + '\'' +
                ", category='" + category + '\'' +
                ", date_taken=" + date_taken +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", is_private=" + is_private +
                ", language='" + language + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mini_thumb_magic=" + mini_thumb_magic +
                ", resolution='" + resolution + '\'' +
                ", tags='" + tags + '\'' +
                ", data='" + data + '\'' +
                ", date_added=" + date_added +
                ", date_modified=" + date_modified +
                ", display_name='" + display_name + '\'' +
                ", height=" + height +
                ", mime_type='" + mime_type + '\'' +
                ", size=" + size +
                ", title='" + title + '\'' +
                ", width=" + width +
                ", id=" + id +
                '}';
    }
}
