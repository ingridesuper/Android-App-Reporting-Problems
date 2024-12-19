package com.example.android_proiect_final_version.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "semnaturi_idei",
        primaryKeys = {"username", "ideeId"},
        foreignKeys = {
                @ForeignKey(entity = Utilizator.class, parentColumns = "username", childColumns = "username", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Idee.class, parentColumns = "id", childColumns = "ideeId", onDelete = ForeignKey.CASCADE)
        })
public class SemnaturaIdee {
        @NonNull
        private String username;
        @NonNull
        private Long ideeId;

        public SemnaturaIdee(@NonNull String username, @NonNull Long ideeId) {
                this.username = username;
                this.ideeId = ideeId;
        }

        @NonNull
        public Long getIdeeId() {
                return ideeId;
        }

        public void setIdeeId(@NonNull Long ideeId) {
                this.ideeId = ideeId;
        }

        @NonNull
        public String getUsername() {
                return username;
        }

        public void setUsername(@NonNull String username) {
                this.username = username;
        }

        @Override
        public String toString() {
                return "SemnaturaIdee{" +
                        "ideeId=" + ideeId +
                        ", username='" + username + '\'' +
                        '}';
        }
}
