package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Column(name = "recipient_id", nullable = false)
    private long recipientId;

    @Column(name = "amount")
    private float amount;

    private float incentive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
    private UserRecord sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false, insertable = false, updatable = false)
    private UserRecord recipient;

    public TransactionRecord() {
    }

    public TransactionRecord(Long senderId, Long recipientId, float amount, float incentive) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.incentive = incentive;
    }

    public long getSenderId() {
        return senderId;
    }

    public Long getId() {
        return id;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }
}
