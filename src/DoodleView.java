/* DoodleView.java
 Main View for the Doodlz app.
 Note: code from the book Android for Programmers: an App-Driven Approach
*/
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

// the main screen that is painted
public class DoodleView extends View 
{
   // used to determine whether user moved a finger enough to draw again   
   private static final float TOUCH_TOLERANCE = 10;

   private Bitmap bitmap; // DRAWING Area for display or saving
   private Canvas bitmapCanvas; // used to draw on bitmap
   private final Paint paintScreen; // used to draw bitmap onto screen
   private final Paint paintLine; // used to draw lines onto bitmap
   
   // Maps of current Paths being drawn and Points in those Paths
   private final Map<Integer, Path> pathMap = new HashMap<Integer, Path>(); 
   private final Map<Integer, Point> previousPointMap = 
      new HashMap<Integer, Point>();
   
   // used to hide/show system bars - may not be needed
   private GestureDetector singleTapDetector; 
      
   // DoodleView constructor initializes the DoodleView
   public DoodleView(Context context, AttributeSet attrs)  
   {
      super(context, attrs); // pass context to View's constructor 
      paintScreen = new Paint(); // used to display bitmap onto screen

      // set the initial display settings for the painted line
      paintLine = new Paint();
      paintLine.setAntiAlias(true); // smooth edges of drawn line
      paintLine.setColor(Color.BLACK); // default color is black
      paintLine.setStyle(Paint.Style.STROKE); // solid line
      paintLine.setStrokeWidth(5); // set the default line width
      paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
      
      // GestureDetector for single taps - may be safe to remove
      singleTapDetector = 
         new GestureDetector(getContext(), singleTapListener);
   } 

   // Method onSizeChanged creates Bitmap and Canvas after app displays
   @Override 
   public void onSizeChanged(int w, int h, int oldW, int oldH)
   {
      bitmap = Bitmap.createBitmap(getWidth(), getHeight(), 
         Bitmap.Config.ARGB_8888);   //8888 stores each pixel color in 4 bytes
      bitmapCanvas = new Canvas(bitmap);
      bitmap.eraseColor(Color.WHITE); // erase the Bitmap with white
   } 
   
   // clear the painting
   public void clear()
   {
      pathMap.clear(); // remove all paths
      previousPointMap.clear(); // remove all previous points
      bitmap.eraseColor(Color.WHITE); // clear the bitmap 
      invalidate(); // refresh the screen
   }
   
   // called each time this View is drawn
   @Override
   protected void onDraw(Canvas canvas) 
   {
      // draw the background screen
      canvas.drawBitmap(bitmap, 0, 0, paintScreen);

      // for each path currently being drawn
      for (Integer key : pathMap.keySet()) 
         canvas.drawPath(pathMap.get(key), paintLine); // draw line
   } 

   //handle touch event
   public boolean onTouchEvent(MotionEvent event) 
   {
      // if a single tap event occurred on KitKat or higher device
      // may be related to removed code      
      /*if (singleTapDetector.onTouchEvent(event))
         return true;*/
      
      // get the event type and the ID of the pointer that caused the event
      int action = event.getActionMasked(); // event type 
      int actionIndex = event.getActionIndex(); // pointer (i.e., finger)
      
      // determine whether touch started, ended or is moving
      if (action == MotionEvent.ACTION_DOWN ||
         action == MotionEvent.ACTION_POINTER_DOWN) 
      {
         touchStarted(event.getX(actionIndex), event.getY(actionIndex), 
            event.getPointerId(actionIndex));
      } 
      else if (action == MotionEvent.ACTION_UP ||
         action == MotionEvent.ACTION_POINTER_UP) 
      {
         touchEnded(event.getPointerId(actionIndex));
      } 
      else 
      {
         touchMoved(event); 
      }
      
      invalidate(); // redraw
      return true;
   } // end method onTouchEvent

   // called when the user touches the screen
   private void touchStarted(float x, float y, int lineID) 
   {
      Path path; // used to store the path for the given touch id
      Point point; // used to store the last point in path

      // if there is already a path for lineID
      if (pathMap.containsKey(lineID)) 
      {
         path = pathMap.get(lineID); // get the Path
         path.reset(); // reset the Path because a new touch has started
         point = previousPointMap.get(lineID); // get Path's last point
      }
      else 
      {
         path = new Path(); 
         pathMap.put(lineID, path); // add the Path to Map
         point = new Point(); // create a new Point
         previousPointMap.put(lineID, point); // add the Point to the Map
      }

      // move to the coordinates of the touch
      path.moveTo(x, y);
      point.x = (int) x;
      point.y = (int) y;
   } // end method touchStarted

   // called when the user drags along the screen
   private void touchMoved(MotionEvent event) 
   {
      // for each of the pointers in the given MotionEvent
      for (int i = 0; i < event.getPointerCount(); i++) 
      {
         // get the pointer ID and pointer index
         int pointerID = event.getPointerId(i);
         int pointerIndex = event.findPointerIndex(pointerID);
            
         // if there is a path associated with the pointer
         if (pathMap.containsKey(pointerID)) 
         {
            // get the new coordinates for the pointer
            float newX = event.getX(pointerIndex);
            float newY = event.getY(pointerIndex);
            
            // get the Path and previous Point associated with 
            // this pointer
            Path path = pathMap.get(pointerID);
            Point point = previousPointMap.get(pointerID);
            
            // calculate how far the user moved from the last update
            float deltaX = Math.abs(newX - point.x);
            float deltaY = Math.abs(newY - point.y);

            // if the distance is significant enough to matter
            if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) 
            {
               // move the path to the new location
               path.quadTo(point.x, point.y, (newX + point.x) / 2,
                  (newY + point.y) / 2);

               // store the new coordinates
               point.x = (int) newX;
               point.y = (int) newY;
            } 
         } 
      }
   } // end method touchMoved

   // called when the user finishes a touch
   private void touchEnded(int lineID) 
   {
      Path path = pathMap.get(lineID); // get the corresponding Path
      bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
      path.reset(); // reset the Path
   } 
