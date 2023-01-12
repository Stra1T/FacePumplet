/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the
	 * interactors in the application, and taking care of any other
	 * initialization that needs to be performed.
	 */
	public void init() {
		initLeftButtons();
		initTopButtons();
		addActionListeners();
		status.addActionListener(this);
		picture.addActionListener(this);
		friend.addActionListener(this);
		canvas = new FacePamphletCanvas();
		add(canvas);
		base = new FacePamphletDatabase();
	}
	/*================================================================================================================
	 *Adds buttons on the left bar.
	 *================================================================================================================
	 */
	private void initLeftButtons() {
		status = new JTextField(TEXT_FIELD_SIZE);
		add(status, WEST);
		StatusButton = new JButton("Change Status");
		add(StatusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		picture = new JTextField(TEXT_FIELD_SIZE);
		add(picture, WEST);
		PictureButton = new JButton("Change Picture");
		add(PictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		friend = new JTextField(TEXT_FIELD_SIZE);
		add(friend, WEST);
		FriendButton = new JButton("Add Friend");
		add(FriendButton, WEST);
	}
	/*================================================================================================================
	 *Adds buttons on the top bar.
	 *================================================================================================================
	 */
	private void initTopButtons() {
		JLabel name1 = new JLabel("Name");
		add(name1, NORTH);
		name = new JTextField(TEXT_FIELD_SIZE);
		add(name, NORTH);
		AddButton = new JButton("Add");
		add(AddButton, NORTH);
		DeleteButton = new JButton("Delete");
		add(DeleteButton, NORTH);
		LookupButton = new JButton("Lookup");
		add(LookupButton, NORTH);
	}

	private JButton StatusButton;
	private JButton PictureButton;
	private JButton FriendButton;
	private JButton AddButton;
	private JButton DeleteButton;
	private JButton LookupButton;
	private JTextField status;
	private JTextField picture;
	private JTextField friend;
	private JTextField name;
	private FacePamphletProfile current;
	private FacePamphletCanvas canvas;
	private FacePamphletDatabase base;


	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked or interactors are used, so you will have to add code
	 * to respond to these actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == AddButton && !name.getText().equals("")) {
			if (!base.containsProfile(name.getText())) {
				current = new FacePamphletProfile(name.getText());
				base.addProfile(current);
				current = base.getProfile(name.getText());
				canvas.displayProfile(current);
				canvas.showMessage("New profile created");
			} else {
				current = base.getProfile(name.getText());
				canvas.displayProfile(current);
				canvas.showMessage("A profile with the name " + name.getText() + " already exists");
			}
		}
		if (e.getSource() == DeleteButton && !name.getText().equals("")) {
			if (base.containsProfile(name.getText())) {
				current = null;
				canvas.removeAll();
				canvas.showMessage("Profile of " + name.getText() + " Deleted");
			} else {
				current = null;
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + name.getText() + " does not exist");
			}
			base.deleteProfile(name.getText());
		}
		if (e.getSource() == LookupButton && !name.getText().equals("")) {
			if (base.containsProfile(name.getText())) {
				current = base.getProfile(name.getText());
				canvas.displayProfile(current);
				canvas.showMessage("Displaying " + name.getText());
			} else {
				current = null;
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + name.getText() + " does not exist");
			}
		}
		if ((e.getSource() == StatusButton && !status.getText().equals("")) || (e.getSource() == status && status.getText() != null)) {
			if (current != null) {
				current.setStatus(status.getText());
				canvas.displayProfile(current);
				canvas.showMessage("Status updated to " + current.getStatus());
			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to change status");
			}
		}
		if ((e.getSource() == PictureButton && !picture.getText().equals("")) || (e.getSource() == picture && picture.getText() != null)) {
			GImage image = null;
			if (current != null) {
				try {
					image = new GImage(picture.getText());
					image.setBounds(LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN + 10, IMAGE_WIDTH, IMAGE_HEIGHT);
					current.setImage(image);
					canvas.displayProfile(current);
					canvas.showMessage("Picture updated");
				} catch (ErrorException ignored) {
					canvas.displayProfile(current);
					canvas.showMessage("Unable to open image file: " + picture.getText());
				}
			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to change picture");
			}
		}
		if ((e.getSource() == FriendButton && !friend.getText().equals("")) || (e.getSource() == friend && friend.getText() != null)) {
			if (current == null) {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to add friend");
			} else {
				if (base.containsProfile(friend.getText())) {
					if (current.addFriend(friend.getText()) && base.getProfile(friend.getText()).addFriend(current.getName())) {
						canvas.displayProfile(current);
						canvas.showMessage(friend.getText() + " added as a friend");
					} else {
						canvas.displayProfile(current);
						canvas.showMessage(current.getName() + " already has " + friend.getText() + " as a friend");
					}
				} else {
					canvas.displayProfile(current);
					canvas.showMessage(friend.getText() + " does not exist");
				}
			}
		}
	}
}
