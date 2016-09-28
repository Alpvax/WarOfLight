package alpvax.waroflight.skill;

import alpvax.characteroverhaul.api.character.ICharacter;
import alpvax.characteroverhaul.api.skill.Skill;
import alpvax.waroflight.emotions.EnumEmotion;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SkillEmotion extends Skill
{
	private final EnumEmotion emotion;
	private final PerkEmotionRing ringPerk;

	public SkillEmotion(EnumEmotion emotion)
	{
		super(emotion.colour());
		this.emotion = emotion;
		GameRegistry.register(ringPerk = new PerkEmotionRing(this));
	}

	public EnumEmotion getEmotion()
	{
		return emotion;
	}

	@Override
	public int getNewLevel(float experience)
	{
		return MathHelper.floor_float(experience % emotion.getSkillThreshold());
	}

	@Override
	public void onLevelChange(int oldLevel, int newLevel, ICharacter character)
	{
		character.aquirePerk(ringPerk);
	}

}
