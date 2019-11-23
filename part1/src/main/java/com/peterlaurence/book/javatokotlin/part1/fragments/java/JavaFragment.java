package com.peterlaurence.book.javatokotlin.part1.fragments.java;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class JavaFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create a ViewModel the first time this Fragment is created.
         * Re-created Fragment receives the same ViewModel instance after device rotation. */
        ViewModelProvider.Factory factory = new CallbackViewModelFactory();
        CallbackViewModel model = new ViewModelProvider(this, factory).get(CallbackViewModel.class);
        model.getPurchasesLiveData("user").observe(this, userPurchases -> {
            // update UI
            System.out.println(userPurchases.purchases);
        });
    }
}
