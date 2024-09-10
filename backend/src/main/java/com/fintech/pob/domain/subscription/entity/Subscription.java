import jakarta.persistence.*;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", nullable = false, updatable = false)
    private Long subscriptionId;

    @OneToOne
    @JoinColumn(name = "target_key", referencedColumnName = "user_key", nullable = false)
    private User targetUser;

    @OneToOne
    @JoinColumn(name = "protect_key", referencedColumnName = "user_key", nullable = false)
    private User protectUser;

    @Column(name = "one_time_transfer_limit")
    private Long oneTimeTransferLimit;  // 1회 이체한도

    @Column(name = "daily_transfer_limit")
    private Long dailyTransferLimit;  // 1일 이체한도
}
