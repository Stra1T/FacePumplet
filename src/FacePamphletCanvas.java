/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, getWidth()/2.0-message.getWidth()/2, getHeight()-BOTTOM_MESSAGE_MARGIN );
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		add(name,LEFT_MARGIN, TOP_MARGIN + name.getAscent());
		GLabel friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth()/2.0, IMAGE_MARGIN + name.getY() + friends.getAscent());
		if(profile.getFriends().hasNext()){
			int i = 0;
			Iterator<String> it = profile.getFriends();
			GLabel friend;
			while(it.hasNext()) {
				i++;
				if(i > profile.friends.size()){
					break;
				}
				friend = new GLabel(it.next());
				friend.setFont(PROFILE_FRIEND_FONT );
				double y = friends.getY()+i+friend.getAscent()*i;
				add(friend, getWidth()/2.0, y);

			}
		}
		if(profile.getImage() != null){
			add(profile.getImage(),LEFT_MARGIN, IMAGE_MARGIN + name.getY());
		}
		else {
			GRect picture = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel image = new GLabel("No Image");
			image.setFont(PROFILE_IMAGE_FONT);
			add(picture, LEFT_MARGIN, IMAGE_MARGIN + name.getY());
			add(image, picture.getX() + picture.getWidth() / 2 - image.getWidth() / 2,
					picture.getY() + picture.getHeight() / 2 + image.getAscent() / 2);
		}
		if(profile.nameAndStatus[1] != null){
			GLabel status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status,LEFT_MARGIN, STATUS_MARGIN + IMAGE_MARGIN + name.getY() + status.getAscent() + IMAGE_HEIGHT);
		}
		else {
			GLabel status = new GLabel("No current status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status,LEFT_MARGIN, STATUS_MARGIN + IMAGE_MARGIN + name.getY() + status.getAscent() + IMAGE_HEIGHT);
		}
	}

	
}
