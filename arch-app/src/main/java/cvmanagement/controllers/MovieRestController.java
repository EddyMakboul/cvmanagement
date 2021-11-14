package cvmanagement.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MovieRestController {

//    @Autowired
//    MovieRepository repo;
//
//    @Autowired
//    JwtTokenProvider provider;
//
//    //@JsonView(Views.Public.class)
//    @GetMapping("/movies")
//    public Iterable<String> getMovies(@RequestParam(required=false) String name, @RequestParam(required=false,defaultValue = "-1") int year) {
//    	//provider.tokenExist(provider.resolveToken(jwt));
//    	ModelMapper modelMapper = new ModelMapper();
//    	if(year != -1 && name != null) {
//    		List<MovieDTO> movieDTO = repo.findByCriteria(name,year).stream().map(m -> modelMapper.map(m, MovieDTO.class))
//    				.collect(Collectors.toList());
//    		List<String> list = new ArrayList<>();
//    		for (MovieDTO movie : movieDTO) {
//				list.add(movie.getCompleteName());
//			}
//    		return list;
//    	} else {
//    		List<MovieDTO> movieDTO = repo.findAll().stream().map(m -> modelMapper.map(m, MovieDTO.class))
//    				.collect(Collectors.toList());
//    		List<String> list = new ArrayList<>();
//    		for (MovieDTO movie1 : movieDTO) {
//				list.add(movie1.getCompleteName());
//			}
//    		return list;
//    	}
//
//    }
//
//    @GetMapping("/movies/{id}")
//    public Movie getMovie(@PathVariable int id) {
//        return repo.findById(id).orElseThrow(MovieNotFoundException::new);
//    }
//
//    @DeleteMapping("/movies/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void deleteMovie(@PathVariable int id) {
//        repo.deleteById(id);
//    }
//    @PostMapping("/movies")
//    public Movie postMovie(@RequestBody MovieDTO m) {
//    	ModelMapper modelMapper = new ModelMapper();
//    	Movie movie = modelMapper.map(m, Movie.class);
//        repo.save(movie);
//        return movie;
//    }
//
//    @PutMapping("/movies")
//    public Movie putMovie(@RequestBody Movie m) {
//    	repo.findById(m.getId()).orElseThrow(MovieNotFoundException::new);
//        repo.save(m);
//        return m;
//    }
//
//    @PatchMapping("/movies/")
//    public void addMovies() {
//
//			repo.save(new Movie("Star wars 4", 1977, //
//					"Il y a bien longtemps, dans une galaxie " + //
//							"lointaine, très lointaine..."));
//			repo.save(new Movie("Star wars 5", 1980, //
//					"Le temps du péril a commencé pour la rébellion..."));
//			repo.save(new Movie("Star wars 6", 1983, //
//					"Luke Skywalker est retourné parmi les siens sur la " + //
//							"planète Tatooine..."));
//			repo.save(new Movie("Star wars 6", 2002, //
//					"Luke Skywalker est retourné parmi les siens sur la " + //
//							"planète Tatooine..."));
//    }

}