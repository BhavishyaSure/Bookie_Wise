package com.example.hyndavirenikunta.bookie;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.hyndavirenikunta.bookie.R;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookRetriever.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookRetriever#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookRetriever extends Fragment implements BookClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView booksRecycler;
    SearchView searchView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<BookPojo> booksList=new ArrayList<>();
    List<BookPojo> bookNames =new ArrayList<>();
    BooksAdapter booksAdapter = new BooksAdapter(null,this);
    private static final String TAG = "BookRetriever";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BookRetriever() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookRetriever.
     */
    // TODO: Rename and change types and number of parameters
    public static BookRetriever newInstance(String param1, String param2) {
        BookRetriever fragment = new BookRetriever();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_book_retriever, container, false);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.content_main);
        databaseReference.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        Log.i(TAG, "onDataChange: "+dataSnapshot.toString());
                        BookPojo bookPojo = snapshot.getValue(BookPojo.class);
                        booksList.add(bookPojo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        booksRecycler = (RecyclerView) rootView.findViewById(R.id.books_recycler);
        booksRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        booksRecycler.setAdapter(booksAdapter);

        searchView = (SearchView) rootView.findViewById(R.id.books_search_view);
        searchView.setActivated(true);
        searchView.setQueryHint("Enter Book Name...");
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = "(.*)"+newText+"(.*)";
                Pattern compiledPattern = Pattern.compile(newText);
                if(booksList!=null) {
                    for(int i=0;i<booksList.size();i++) {
                        Matcher matcher = compiledPattern.matcher(booksList.get(i).getBookName());
                        if(matcher.find()) {
                            bookNames.add(booksList.get(i));
                        }
                    }
                    booksAdapter.replaceData(bookNames);
                }
                return false;
            }
        });
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBookClick(BookPojo book) {
        Toast.makeText(getActivity(), "Book Name" + book.getBookName(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(book.getBookName());
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Book Author: "+book.getBookAuthor()+"\nBook Count: "+book.getBookCount());
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
