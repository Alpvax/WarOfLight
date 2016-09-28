package alpvax.waroflight.skill;

import alpvax.characteroverhaul.api.character.ICharacter;
import alpvax.characteroverhaul.api.perk.Perk;

public class PerkEmotionRing extends Perk
{
	public PerkEmotionRing(SkillEmotion skillTree)
	{
		super("ring_" + skillTree.getEmotion().colour(), skillTree);
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldDisplay(ICharacter character)
	{
		return false;
	}

	@Override
	public boolean canUnlock(int level, ICharacter character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean applyUnlockCost(int levelToUnlock, ICharacter character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxLevel(ICharacter character)
	{
		return 1;
	}

	@Override
	public void onLevelChange(int oldLevel, int newLevel, ICharacter character)
	{
		// TODO Auto-generated method stub

	}
}
