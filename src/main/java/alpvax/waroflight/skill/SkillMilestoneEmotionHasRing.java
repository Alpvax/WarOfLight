package alpvax.waroflight.skill;

import java.util.Random;

import alpvax.abilities.api.skill.ISkillHandler;
import alpvax.abilities.api.skill.SkillInstance;
import net.minecraft.nbt.NBTTagCompound;

public class SkillMilestoneEmotionHasRing extends SkillMilestoneEmotion
{

	public SkillMilestoneEmotionHasRing(SkillEmotion skill)
	{
		super(skill, "HasRing");
	}

	@Override
	public boolean hasAchieved(SkillInstance skillInstance)
	{
		return super.hasAchieved(skillInstance) && new Random().nextInt();
	}

	@Override
	public void onAchieved(ISkillHandler handler)
	{
		// TODO:Add ring
	}

	@Override
	public void onUnachieved(ISkillHandler handler)
	{
		// TODO:Remove ring

	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		return nbt;
	}
}
