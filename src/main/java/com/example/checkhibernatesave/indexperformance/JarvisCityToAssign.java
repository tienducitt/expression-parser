package com.example.checkhibernatesave.indexperformance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class JarvisCityToAssign {
    @Id
    @Column(name = "city_code", nullable = false, unique = true)
    private String cityCode;

    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "next_cycle_due_at", nullable = false, columnDefinition="DATETIME(6) NOT NULL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextCycleDueAt;

    @Column(name = "period", nullable = false)
    private int period;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    // for hibernate
    public JarvisCityToAssign() {

    }

    public JarvisCityToAssign(String cityCode, Date updateTime, Date nextCycleDueAt, int period,
        boolean isEnabled) {
        this.cityCode = cityCode;
        this.updateTime = updateTime;
        this.nextCycleDueAt = nextCycleDueAt;
        this.period = period;
        this.isEnabled = isEnabled;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getNextCycleDueAt() {
        return nextCycleDueAt;
    }

    public int getPeriod() {
        return period;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setNextCycleDueAt(Date nextCycleDueAt) {
        this.nextCycleDueAt = nextCycleDueAt;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "JarvisCityToAssign{" +
            "cityCode='" + cityCode + '\'' +
            ", updateTime=" + updateTime +
            ", nextCycleDueAt=" + nextCycleDueAt +
            ", period=" + period +
            ", isEnabled=" + isEnabled +
            '}';
    }
}
