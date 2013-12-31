package com.a1biz.soccerhub;

import com.a1biz.soccerhub.carousel.Carousel;
import com.a1biz.soccerhub.carousel.CarouselAdapter;
import com.a1biz.soccerhub.carousel.CarouselAdapter.OnItemClickListener;
import com.a1biz.soccerhub.carousel.CarouselAdapter.OnItemSelectedListener;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SpinningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinning);
		
		Carousel carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setOnItemClickListener(new OnItemClickListener()
        {
			public void onItemClick(CarouselAdapter<?> parent, View view,
					int position, long id) 
			{	
				Intent activity = null; 
//				menu.findItem(R.id.favourite).setChecked(true);	
				if(position == 0)
					activity = new Intent(getApplicationContext(), MatchActivity.class);
				else if(position == 1)
					activity = new Intent(getApplicationContext(), TodayActivity.class);
				else if(position == 2)
					activity = new Intent(getApplicationContext(), FavouriteActivity.class);
				else if(position == 3)
					if(utilityData.isLogin(getApplicationContext()) == true)
						activity = new Intent(getApplicationContext(), MemberActivity.class);
		            else
		            	activity = new Intent(getApplicationContext(), LoginActivity.class);
				else if(position == 4)
					activity = new Intent(getApplicationContext(), ReadDayActitity.class);
				
				activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(activity);
	            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
			}
        });

        carousel.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(CarouselAdapter<?> parent, View view,
					int position, long id) {
				
		        final TextView txt = (TextView)(findViewById(R.id.selected_item));
		        
				switch(position){
				case 0:
					txt.setText("The cat (Felis catus), also known as the domestic cat or housecat to distinguish it from other felids and felines, is a small, usually furry, domesticated, carnivorous mammal that is valued by humans for its companionship and for its ability to hunt vermin and household pests. Cats have been associated with humans for at least 9,500 years, and are currently the most popular pet in the world. Owing to their close association with humans, cats are now found almost everywhere in the world.");
					break;
				case 1:
					txt.setText("The hippopotamus (Hippopotamus amphibius), or hippo, from the ancient Greek for \"river horse\" (ἱπποπόταμος), is a large, mostly herbivorous mammal in sub-Saharan Africa, and one of only two extant species in the family Hippopotamidae (the other is the Pygmy Hippopotamus.) After the elephant, the hippopotamus is the third largest land mammal and the heaviest extant artiodactyl.");
					break;
				case 2:
					txt.setText("A monkey is a primate, either an Old World monkey or a New World monkey. There are about 260 known living species of monkey. Many are arboreal, although there are species that live primarily on the ground, such as baboons. Monkeys are generally considered to be intelligent. Unlike apes, monkeys usually have tails. Tailless monkeys may be called \"apes\", incorrectly according to modern usage; thus the tailless Barbary macaque is called the \"Barbary ape\".");
					break;
				case 3:
					txt.setText("A mouse (plural: mice) is a small mammal belonging to the order of rodents. The best known mouse species is the common house mouse (Mus musculus). It is also a popular pet. In some places, certain kinds of field mice are also common. This rodent is eaten by large birds such as hawks and eagles. They are known to invade homes for food and occasionally shelter.");
					break;
				case 4:
					txt.setText("The giant panda, or panda (Ailuropoda melanoleuca, literally meaning \"black and white cat-foot\") is a bear native to central-western and south western China.[4] It is easily recognized by its large, distinctive black patches around the eyes, over the ears, and across its round body. Though it belongs to the order Carnivora, the panda's diet is 99% bamboo.");
					break;
				case 5:
					txt.setText("Rabbits (or, colloquially, bunnies) are small mammals in the family Leporidae of the order Lagomorpha, found in several parts of the world. There are eight different genera in the family classified as rabbits, including the European rabbit (Oryctolagus cuniculus), cottontail rabbits (genus Sylvilagus; 13 species), and the Amami rabbit (Pentalagus furnessi, an endangered species on Amami Ōshima, Japan)");
					break;
				}
				
			}

			public void onNothingSelected(CarouselAdapter<?> parent) {
			}
        } );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        
        case R.id.spinning:
            Intent spinningActivity = new Intent(getApplicationContext(), SpinningActivity.class);
            startActivity(spinningActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
        
        case R.id.home:
            Intent matchActivity = new Intent(getApplicationContext(), MatchActivity.class);
            startActivity(matchActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.leagueToday:
            Intent leagueActivity = new Intent(getApplicationContext(), TodayActivity.class);
            startActivity(leagueActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
 
        case R.id.member:
            Intent memberActivity;
            if(utilityData.isLogin(this) == true)
            	memberActivity = new Intent(getApplicationContext(), MemberActivity.class);
            else
            	memberActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(memberActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.favourite:
            Intent favouriteActivity = new Intent(getBaseContext(), FavouriteActivity.class);
            startActivity(favouriteActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.readDays:
            Intent readDaysActivity = new Intent(getBaseContext(), ReadDayActitity.class);
            startActivity(readDaysActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

}
