package cn.cookiestudio.aweadyffa.playersetting;

import cn.nukkit.level.ParticleEffect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlayerSettingEntry{
    boolean showAttackParticle;
    boolean randomTp;
    boolean showEnemyHealthInActionbar;
    String particleType = ParticleEffect.CRITICAL_HIT.getIdentifier();
}
