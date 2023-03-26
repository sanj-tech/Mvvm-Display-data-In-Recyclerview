# Mvvm-Display-data-In-Recyclerview
MVVM (Model-View-ViewModel) is an architectural pattern that emphasizes separation of concerns, testability, and maintainability of code. Here's how MVVM works in Kotlin:

Model: The model is responsible for representing the data and the business logic of your application. It can be a simple data class or a more complex repository that interacts with your data source.

View: The view is responsible for displaying the user interface and capturing user input. In Android, this is represented by XML layout files and activity/fragment classes.

ViewModel: The ViewModel acts as a mediator between the view and the model. It contains the presentation logic and exposes data to the view through observable properties. In Kotlin, it's typically implemented as a class that extends the Android ViewModel class.

Data Binding: Data binding is a mechanism that allows you to bind your UI components to the ViewModel in a declarative way, without having to write boilerplate code to update the UI. You can use Kotlin's synthetic binding or Android's view binding to achieve this.

Observables: Observables are used to notify the ViewModel about changes in the model, so that it can update the UI accordingly. In Kotlin, you can use LiveData or RxJava for this purpose.

By using the MVVM pattern in Kotlin, you can ensure that your code is well-organized, modular, and testable. It also helps you to avoid common pitfalls such as tight coupling and spaghetti code.

Steps:  Let's assume that you have an Android app that displays a list of books. Here's how you could implement MVVM:

Model:

data class Book(val title: String, val author: String, val description: String)
This is a simple data class that represents a book.

View:

class BookListActivity : AppCompatActivity() {
private lateinit var viewModel: BookListViewModel

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_book_list)

    viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)

    val adapter = BookListAdapter(this, viewModel.books)
    val recyclerView = findViewById<RecyclerView>(R.id.book_list_recycler_view)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    viewModel.loadBooks()
}
}
This is a simple activity that displays a list of books using a RecyclerView. It creates an instance of the BookListViewModel and passes the list of books to the adapter.

ViewModel:

class BookListViewModel : ViewModel() {
val books = MutableLiveData<List<Book>>()

fun loadBooks() {
    // Make a network request to fetch the list of books
    // Once the response is received, update the books list
    books.value = listOf(
        Book("The Alchemist", "Paulo Coelho", "A magical story about following your dreams"),
        Book("To Kill a Mockingbird", "Harper Lee", "A powerful novel about racism and injustice")
    )
}
}
This is a simple ViewModel that exposes a list of books as an observable property. It also has a method called loadBooks() that fetches the list of books and updates the observable property.

Data Binding:

class BookListAdapter(private val context: Context, private val books: LiveData<List<Book>>)
: RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.list_item_book, parent, false)
    return ViewHolder(view)
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val book = books.value?.get(position)
    holder.titleTextView.text = book?.title
    holder.authorTextView.text = book?.author
    holder.descriptionTextView.text = book?.description
}

inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
    val authorTextView: TextView = itemView.findViewById(R.id.author_text_view)
    val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
}
}
This is a simple adapter that binds the list of books to the RecyclerView using data binding.

Observables:

viewModel.books.observe(this, Observer<List<Book>> { books ->
adapter.notifyDataSetChanged()
})
This code observes changes in the books property of the ViewModel and updates the adapter when the list of books changes.

That's it! This is a very basic example of how MVVM can work in Kotlin. Of course, in a real-world app, you would have more complex models, views, and view models. But the core principles would remain the same.

next: The code viewModel.books.observe(this, Observer<List<Book>> { books -> adapter.notifyDataSetChanged() }) is typically written in the View (Activity, Fragment, or View) class, where viewModel is an instance of the ViewModel, and adapter is an instance of the RecyclerView adapter.

Here's an example of how this code can be implemented in a Fragment class:

class BookListFragment : Fragment() {
    private lateinit var viewModel: BookListViewModel
    private lateinit var adapter: BookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.book_list_recycler_view)
        adapter = BookListAdapter(requireContext(), viewModel.books)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)
        viewModel.loadBooks()
        viewModel.books.observe(viewLifecycleOwner, Observer<List<Book>> { books ->
            adapter.notifyDataSetChanged()
        })
    }
}
