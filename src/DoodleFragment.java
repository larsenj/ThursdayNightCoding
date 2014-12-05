// DoodleFragment.java
// Fragment in which the DoodleView is displayed

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class DoodleFragment extends Fragment
{
   private DoodleView doodleView; // handles touch events and draws
   private float lastAcceleration; 
   private boolean dialogOnScreen = false;
   
   // called when Fragment's view needs to be created
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
   {
      super.onCreateView(inflater, container, savedInstanceState);    
      View view = 
         inflater.inflate(R.layout.fragment_doodle, container, false);
               
      //setHasOptionsMenu(true); // this fragment has menu items to display - may not be needed

      // get reference to the DoodleView
      doodleView = (DoodleView) view.findViewById(R.id.doodleView);
      
      return view;
   }
      
   
   private void confirmErase()
   {
      EraseImageDialogFragment fragment = new EraseImageDialogFragment();
      fragment.show(getFragmentManager(), "erase dialog");
   } // end method confirmErase

   // display this fragment's menu items
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
   {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.doodle_fragment_menu, menu);
   }
   public DoodleView getDoodleView()
   {
      return doodleView;
   }

   // indicates whether a dialog is displayed
   public void setDialogOnScreen(boolean visible)
   {
      dialogOnScreen = visible;  
   }
}

/**************************************************************************
 * (C) Copyright 1992-2012 by Deitel & Associates, Inc. and               * 
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     * 
 * best efforts in preparing the book. These efforts include the          * 
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       * 
 * no warranty of any kind, expressed or implied, with regard to these    * 
 * programs or to the documentation contained in these books. The authors * 
 * and publisher shall not be liable in any event for incidental or       * 
 * consequential damages in connection with, or arising out of, the       * 
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/

