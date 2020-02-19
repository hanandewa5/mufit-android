package com.nostratech.mufit.consumer.body_composition;

class BodyCompositionContract {
     interface View {
         void openBodyCompositionWebView(String url);
     }

     interface Presenter {
         void generateBodyCompositionUrl(String b2bId);
     }
}
