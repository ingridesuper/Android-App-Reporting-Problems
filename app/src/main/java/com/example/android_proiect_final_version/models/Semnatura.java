package com.example.android_proiect_final_version.models;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "semnaturi",
        primaryKeys = {"username", "problemaId"},
        foreignKeys = {
                @ForeignKey(entity = Utilizator.class, parentColumns = "username", childColumns = "username", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Problema.class, parentColumns = "id", childColumns = "problemaId", onDelete = ForeignKey.CASCADE)
        }
)
public class Semnatura {
    @NonNull
    private String username;
    @NonNull
    private Long problemaId;

    public Semnatura(@NonNull String username, @NonNull Long problemaId) {
        this.username = username;
        this.problemaId = problemaId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public Long getProblemaId() {
        return problemaId;
    }

    public void setProblemaId(@NonNull Long problemaId) {
        this.problemaId = problemaId;
    }

    @Override
    public String toString() {
        return "Semnatura{" +
                "problemaId=" + problemaId +
                ", username='" + username + '\'' +
                '}';
    }
}
