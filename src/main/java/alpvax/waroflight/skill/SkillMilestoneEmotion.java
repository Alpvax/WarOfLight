package alpvax.waroflight.skill;

import alpvax.abilities.api.skill.ISkillHandler;
import alpvax.abilities.api.skill.SkillInstance;
import alpvax.abilities.api.skill.SkillMilestone;
import alpvax.waroflight.emotions.EnumEmotion;

public class SkillMilestoneEmotion extends SkillMilestone
{
	private final EnumEmotion emotion;

	public SkillMilestoneEmotion(SkillEmotion skill)
	{
		super(skill, "Mastered");
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

	@Override
	public void onAchieved(ISkillHandler handler)
	{
		//TODO:Grant ring
		if(emotion != EnumEmotion.LIFE)
		{
			handler.getInstance(EnumEmotion.LIFE.getSkill()).markRequiresUpdate();
		}
	}

	@Override
	public void onUnachieved(ISkillHandler handler)
	{
		//TODO:Remove ring
	}

}
