package com.porfiriopartida.acomo.call;

import android.content.Context;
import android.media.AudioManager;

import com.porfiriopartida.acomo.config.Config;
import com.porfiriopartida.acomo.utils.ContactsUtils;

import java.util.Date;

/**
 * Template from ftvs
 *
 * https://gist.github.com/ftvs
 * https://gist.github.com/ftvs/e61ccb039f511eb288ee
 */
public class CallListener extends PhonecallReceiver {
    private static int previous;
    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start)
    {
        String contactName = ContactsUtils.retrieveContactName(ctx, number);
        AudioManager audiomanager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
        previous = audiomanager.getRingerMode();

        if(contactName == null){
            callMute(ctx);
        }
    }

    private void rollbackAudioConfiguration(Context ctx){
        if(!Config.isCallMuteEnabled(ctx)){
            //No need to rollback audio since the app is disabled.
            return;
        }

        AudioManager audiomanager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
        audiomanager.setRingerMode(previous);
    }
    private void callMute(Context ctx){
        if(!Config.isCallMuteEnabled(ctx)){
            //No need to mute audio since the app is disabled.
            return;
        }

        AudioManager audiomanager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
        audiomanager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start)
    {
        rollbackAudioConfiguration(ctx);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        rollbackAudioConfiguration(ctx);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {
        rollbackAudioConfiguration(ctx);
    }
}
