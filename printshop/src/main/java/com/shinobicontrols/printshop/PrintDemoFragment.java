/*
Copyright 2014 Scott Logic Ltd

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.shinobicontrols.printshop;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrintDemoFragment extends Fragment implements ImageAndTextContainer {

    public PrintDemoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_print, container, false);

        // Wire up some button handlers
        rootView.findViewById(R.id.print_image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintHelper printHelper = new PrintHelper(getActivity());
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                // Get the image
                Bitmap image = getImage();
                if (image != null) {
                    // Send it to the print helper
                    printHelper.printBitmap("PrintShop", image);
                }

            }
        });

        final ImageAndTextContainer imageAndTextContainer = this;

        rootView.findViewById(R.id.print_page_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PrintDocumentAdapter
                PrintShopPrintDocumentAdapter adapter = new PrintShopPrintDocumentAdapter(imageAndTextContainer, getActivity());
                // Get the print manager from the context
                PrintManager printManager = (PrintManager)getActivity().getSystemService(Context.PRINT_SERVICE);
                // And print the document
                printManager.print("PrintShop", adapter, null);
            }
        });

        return rootView;
    }

    @Override
    public String getText() {
        TextView textView = (TextView) getView().findViewById(R.id.textView);
        return textView.getText().toString();
    }

    @Override
    public Bitmap getImage() {
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        Bitmap image = null;
        // Get the image
        if ((imageView.getDrawable()) != null) {
            // Send it to the print helper
            image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
        return image;
    }
}
