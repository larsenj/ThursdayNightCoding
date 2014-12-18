/* DoodleView.java
http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-interface-creation--mobile-19021
http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-touch-interaction--mobile-19202
http://code.tutsplus.com/tutorials/android-sdk-create-a-drawing-app-essential-functionality--mobile-19328
*/

package com.mathproblemconstructor;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;

public class DoodleView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //erase flag
    private boolean erase=false;
    private float brushSize, lastBrushSize;


    public DoodleView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }


    private void setupDrawing(){

        //prepare for drawing and setup paint stroke properties
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    //draw the view - will be called after touch event
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    //register user touches as drawing action
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate(); //this causes the onDraw() method to execute
        return true;

    }

    //update color
    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    //set erase true or false
    public void setErase(boolean isErase){
        erase=isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }
    //set brush size
    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    //get and set last brush size
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }

    public float getLastBrushSize(){
        return lastBrushSize;
    }

    //start new drawing
    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}











































/*
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
 /*
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
*/