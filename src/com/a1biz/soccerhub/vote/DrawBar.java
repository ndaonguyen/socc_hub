package com.a1biz.soccerhub.vote;

import com.a1biz.soccerhub.conf.CONFIG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class DrawBar extends View {
    Paint paint     = new Paint();
    Paint paintLine = new Paint();
    Paint paintText = new Paint();
    double[] percentArr = {0,0,0};
    int lineSpace   = 20;
    String[] teamName;
    int   [] numVotes;
    Context context;
    int width;
    int height;
    int topChart;

    /*
     *  numVotes : vote's number of teams
     *  top      : Top of the chart, where we can put chart into
     *  teamNames: team names array
     */
    public DrawBar(Context con,int[] numVote, int top, String[] teamNames) 
    {
        super(con);    
        context  = con;
        DisplayMetrics metrics    = context.getResources().getDisplayMetrics();
    	width    = metrics.widthPixels ;
    	height   = CONFIG.CHART_HEIGHT;
    	topChart = top;
    	teamName = teamNames;
    	numVotes = numVote;
    	
    	// cal percent
    	int allVote  = 0;
    	for(int i = 0; i < numVote.length; i++ )
    		allVote +=numVote[i];
    	
    	for(int k=0; k < numVote.length; k++ )
    	{
    		double value = numVote[k]/(double)allVote;
    		percentArr[k] = value;
    	}
    }

    @Override
    public void onDraw(Canvas canvas) {
    	int colSpace = (width - 3*CONFIG.COL_WIDTH - 2*CONFIG.SPACE_LEFT_RIGHT)/2;
    	
    	int leftBar   = 0;
    	int topBar    = topChart;
    	int rightBar  = 0;
    	int bottomBar = topChart + CONFIG.CHART_HEIGHT 
    					- CONFIG.CHART_INFO_HEIGHT 
    					+ CONFIG.SPACE_TOP_N_CHART ;
    	
    	int lenArrCo = percentArr.length;
    	if(lenArrCo < CONFIG.NUM_COL)
    		return;
    	
    	paint.setColor(CONFIG.COLOR_VOTE);
        paint.setStrokeWidth(3);
        
    	for(int i = 0;i< CONFIG.NUM_COL; i++)
    	{
            leftBar   = CONFIG.SPACE_LEFT_RIGHT + i*CONFIG.COL_WIDTH + i*colSpace;
            rightBar  = leftBar + CONFIG.COL_WIDTH;
            topBar    = topChart  + CONFIG.CHART_HEIGHT  - (int) (CONFIG.CHART_HEIGHT*percentArr[i]) 
            			- CONFIG.CHART_INFO_HEIGHT  
            			+ CONFIG.SPACE_TOP_N_CHART; 
            		
            canvas.drawRect(leftBar, topBar, rightBar, bottomBar, paint);
            
            //draw Text : num votes and team's name
            paintText.setColor(CONFIG.COLOR_VOTE); 
            paintText.setTextSize(CONFIG.TEXT_SIZE); 
            
            int xMidPoint = leftBar + CONFIG.COL_WIDTH/2;
            int yTextVote = bottomBar + lineSpace ;
            canvas.drawText(String.valueOf(numVotes[i]), xMidPoint, yTextVote, paintText); 
            
            float textWidth = paintText.measureText(teamName[i]);
            int xTextName   = xMidPoint - (int) textWidth/2;
            int yTextName   = bottomBar + lineSpace*2;
            
            canvas.drawText(teamName[i] , xTextName , yTextName, paintText); 
            
            //draw %
            float roundPercent = (float) Math.round(percentArr[i] * 100) / 100;
            float percentWidth  = paintText.measureText(String.valueOf(roundPercent));
            int xTextPercent    = xMidPoint - (int)percentWidth/2;
            int yTextPercent    = topBar - lineSpace/2;
            
            canvas.drawText(String.valueOf(roundPercent), xTextPercent, yTextPercent, paintText);
    	}
    	int startX = CONFIG.SPACE_LEFT_RIGHT - 10;
    	int stopX  = CONFIG.SPACE_LEFT_RIGHT + CONFIG.NUM_COL*CONFIG.COL_WIDTH + (CONFIG.NUM_COL-1)*colSpace +10;
    	
    	paintLine.setColor(CONFIG.COLOR_VOTE);
    	paintLine.setStrokeWidth(0);
    	canvas.drawLine(startX, bottomBar, stopX, bottomBar, paintLine);
    }

}