package cc.utils;

/**
 * Created by jakihappycity on 06.11.15.
 */
public abstract class ScheduledServerAction
{
    public int actionTime;

    public ScheduledServerAction(int time)
    {
        actionTime = time;
    }

    public abstract void execute();
}