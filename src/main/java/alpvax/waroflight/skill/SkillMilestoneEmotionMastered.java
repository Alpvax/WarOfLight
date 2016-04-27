package alpvax.waroflight.skill;

import alpvax.abilities.api.skill.ISkillHandler;
import alpvax.waroflight.emotions.EnumEmotion;

public class SkillMilestoneEmotionMastered extends SkillMilestoneEmotion
{
	public SkillMilestoneEmotionMastered(SkillEmotion skill)
	{
		super(skill, "Mastered");
	}

	@Override
	public void onAchieved(ISkillHandler handler)
	{
		if(emotion != EnumEmotion.LIFE)
		{
			handler.getInstance(EnumEmotion.LIFE.getSkill()).markRequiresUpdate();
		}
	}

	@Override
	public void onUnachieved(ISkillHandler handler)
	{
		//DO NOTHING
	}

}
