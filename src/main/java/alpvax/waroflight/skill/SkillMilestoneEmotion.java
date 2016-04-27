package alpvax.waroflight.skill;

import alpvax.abilities.api.skill.SkillInstance;
import alpvax.abilities.api.skill.SkillMilestone;
import alpvax.waroflight.emotions.EnumEmotion;

public abstract class SkillMilestoneEmotion extends SkillMilestone
{
	protected final EnumEmotion emotion;

	public SkillMilestoneEmotion(SkillEmotion skill, String key)
	{
		super(skill, key);
		emotion = skill.getEmotion();
	}

	@Override
	public boolean hasAchieved(SkillInstance skillInstance)
	{
		double value = skillInstance.getValue();
		switch(emotion){
		case LIFE:
			return false;//TODO:Check other states
		default:
			return value >= emotion.getThreshold();
		}
	}

}
