package ru.tayviscon.mafia.master.bot.domain.entity.mafia.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tayviscon.mafia.master.bot.domain.entity.DomainObjectEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "mafia_user")
public class MafiaUserEntity extends DomainObjectEntity {

    private Long telegramUserId;
    private String surname;
    private String name;
    private String patronymic;
    private String username;

}
