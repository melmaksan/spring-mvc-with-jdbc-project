package com.epam.esm.entities;

public class GiftCertificateToTag {

    private long giftCertificateId;
    private int tagId;

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "GiftCertificateToTag{" +
                "giftCertificateId=" + giftCertificateId +
                ", serviceId=" + tagId +
                '}';
    }
}
