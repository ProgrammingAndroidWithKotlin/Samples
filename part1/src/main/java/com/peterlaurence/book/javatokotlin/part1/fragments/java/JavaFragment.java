package com.peterlaurence.book.javatokotlin.part1.fragments.java;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class JavaFragment extends Fragment {
    private static final String USER_KEY = "user";

    public static JavaFragment newInstance(String user) {
        Bundle args = new Bundle();
        args.putString(USER_KEY, user);
        JavaFragment fragment = new JavaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get the user from the Bundle */
        Bundle args = getArguments();
        String user = args != null ? args.getString(USER_KEY) : null;
        if (user == null) return;

        /* Create a ViewModel the first time this Fragment is created.
         * Re-created Fragment receives the same ViewModel instance after device rotation. */
        ViewModelProvider.Factory factory = new PurchaseViewModelFactory();
        PurchasesViewModel model = new ViewModelProvider(this, factory).get(PurchasesViewModel.class);
        model.getPurchasesLiveData(user).observe(this, userPurchases -> {
            // update UI
            System.out.println(userPurchases.purchases);
        });
    }
}
