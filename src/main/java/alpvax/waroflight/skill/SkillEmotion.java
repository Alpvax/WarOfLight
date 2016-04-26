package alpvax.waroflight.skill;

import alpvax.abilities.api.skill.Skill;
import alpvax.abilities.api.skill.SkillMilestone;
import alpvax.waroflight.emotions.EnumEmotion;

public class SkillEmotion extends Skill
{
	private final EnumEmotion emotion;

	public SkillEmotion(EnumEmotion emotion)
	{
		super("emotion_" + emotion.name().toLowerCase());
		this.emotion = emotion;
		addMilestone(getEmotionMilestone());
	}

	protected SkillMilestone getEmotionMilestone()
	{
		return new SkillMilestoneEmotion(this);
	}

	public EnumEmotion getEmotion()
	{
		return emotion;
	}

	@Override
	protected double getBaseValue()
	{
		return 0;
	}


}
