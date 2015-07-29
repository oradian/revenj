package gen.model;


public class Boot implements org.revenj.extensibility.SystemAspect {

	public static org.revenj.patterns.ServiceLocator configure(String jdbcUrl) throws java.io.IOException {
		java.util.Properties properties = new java.util.Properties();
		java.io.File revProps = new java.io.File("revenj.properties");
		if (revProps.exists() && revProps.isFile()) {
			properties.load(new java.io.FileReader(revProps));
		}
		return configure(jdbcUrl, properties);
	}

	public static org.revenj.patterns.ServiceLocator configure(String jdbcUrl, java.util.Properties properties) throws java.io.IOException {
		properties.setProperty("namespace", "gen.model");
		java.util.function.Function<org.revenj.patterns.ServiceLocator, java.sql.Connection> factory = c -> {
			try {
				return java.sql.DriverManager.getConnection(jdbcUrl, properties);
			} catch (java.sql.SQLException e) {
				throw new RuntimeException(e);
			}
		};
		return org.revenj.Revenj.setup(factory, properties, java.util.Optional.<ClassLoader>empty(), java.util.Collections.singletonList((org.revenj.extensibility.SystemAspect) new Boot()).iterator());
	}

	public void configure(org.revenj.extensibility.Container container) throws java.io.IOException {
		java.util.List<org.revenj.postgres.ObjectConverter.ColumnInfo> columns = new java.util.ArrayList<>();
		java.util.Properties properties = container.resolve(java.util.Properties.class);
		String prevNamespace = properties.getProperty("namespace");
		if (prevNamespace != null && !"gen.model".equals(prevNamespace)) {
				throw new java.io.IOException("Different namespace already defined in Properties file. Trying to add namespace=gen.model. Found: " + prevNamespace);
		}
		properties.setProperty("namespace", "gen.model");
		try (java.sql.Connection connection = container.resolve(java.sql.Connection.class);
				java.sql.PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"-NGS-\".load_type_info()");
				java.sql.ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				columns.add(
						new org.revenj.postgres.ObjectConverter.ColumnInfo(
								rs.getString("type_schema"),
								rs.getString("type_name"),
								rs.getString("column_name"),
								rs.getString("column_schema"),
								rs.getString("column_type"),
								rs.getShort("column_index"),
								rs.getBoolean("is_not_null"),
								rs.getBoolean("is_ngs_generated")
						)
				);

			}
		} catch (java.sql.SQLException e) {
			throw new java.io.IOException(e);
		}
		container.registerInstance(org.revenj.patterns.ServiceLocator.class, container, false);
		
		
		gen.model.test.converters.SimpleConverter test$converter$SimpleConverter = new gen.model.test.converters.SimpleConverter(columns);
		container.register(test$converter$SimpleConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.Simple>>(){}.type, test$converter$SimpleConverter, false);
		
		gen.model.test.converters.LazyLoadConverter test$converter$LazyLoadConverter = new gen.model.test.converters.LazyLoadConverter(columns);
		container.register(test$converter$LazyLoadConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.LazyLoad>>(){}.type, test$converter$LazyLoadConverter, false);
		
		gen.model.test.converters.SingleDetailConverter test$converter$SingleDetailConverter = new gen.model.test.converters.SingleDetailConverter(columns);
		container.register(test$converter$SingleDetailConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.SingleDetail>>(){}.type, test$converter$SingleDetailConverter, false);
		
		gen.model.test.converters.CompositeConverter test$converter$CompositeConverter = new gen.model.test.converters.CompositeConverter(columns);
		container.register(test$converter$CompositeConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.Composite>>(){}.type, test$converter$CompositeConverter, false);
		
		gen.model.test.converters.CompositeListConverter test$converter$CompositeListConverter = new gen.model.test.converters.CompositeListConverter(columns);
		container.register(test$converter$CompositeListConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.CompositeList>>(){}.type, test$converter$CompositeListConverter, false);
		
		gen.model.test.converters.EntityConverter test$converter$EntityConverter = new gen.model.test.converters.EntityConverter(columns);
		container.register(test$converter$EntityConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.Entity>>(){}.type, test$converter$EntityConverter, false);
		
		gen.model.test.converters.ClickedConverter test$converter$ClickedConverter = new gen.model.test.converters.ClickedConverter(columns);
		container.register(test$converter$ClickedConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.test.Clicked>>(){}.type, test$converter$ClickedConverter, false);
		
		gen.model.Seq.converters.NextConverter Seq$converter$NextConverter = new gen.model.Seq.converters.NextConverter(columns);
		container.register(Seq$converter$NextConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.Seq.Next>>(){}.type, Seq$converter$NextConverter, false);
		
		gen.model.mixinReference.converters.SpecificReportConverter mixinReference$converter$SpecificReportConverter = new gen.model.mixinReference.converters.SpecificReportConverter(columns);
		container.register(mixinReference$converter$SpecificReportConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.mixinReference.SpecificReport>>(){}.type, mixinReference$converter$SpecificReportConverter, false);
		
		gen.model.mixinReference.converters.AuthorConverter mixinReference$converter$AuthorConverter = new gen.model.mixinReference.converters.AuthorConverter(columns);
		container.register(mixinReference$converter$AuthorConverter);
		container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.mixinReference.Author>>(){}.type, mixinReference$converter$AuthorConverter, false);
		test$converter$SimpleConverter.configure(container);
		test$converter$LazyLoadConverter.configure(container);
		gen.model.test.LazyLoad.__setupSequenceID();
		
		container.register(gen.model.test.repositories.LazyLoadRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.test.LazyLoad>>(){}.type, gen.model.test.repositories.LazyLoadRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.test.LazyLoad>>(){}.type, gen.model.test.repositories.LazyLoadRepository::new, false);
		test$converter$SingleDetailConverter.configure(container);
		gen.model.test.SingleDetail.__setupSequenceID();
		
		container.register(gen.model.test.repositories.SingleDetailRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.test.SingleDetail>>(){}.type, gen.model.test.repositories.SingleDetailRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.test.SingleDetail>>(){}.type, gen.model.test.repositories.SingleDetailRepository::new, false);
		test$converter$CompositeConverter.configure(container);
		
		container.register(gen.model.test.repositories.CompositeRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.test.Composite>>(){}.type, gen.model.test.repositories.CompositeRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.test.Composite>>(){}.type, gen.model.test.repositories.CompositeRepository::new, false);
		test$converter$CompositeListConverter.configure(container);
		
		container.register(gen.model.test.repositories.CompositeListRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.test.CompositeList>>(){}.type, gen.model.test.repositories.CompositeListRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.test.CompositeList>>(){}.type, gen.model.test.repositories.CompositeListRepository::new, false);
		test$converter$EntityConverter.configure(container);
		test$converter$ClickedConverter.configure(container);
		
		container.register(gen.model.test.repositories.ClickedRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.test.Clicked>>(){}.type, gen.model.test.repositories.ClickedRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.test.Clicked>>(){}.type, gen.model.test.repositories.ClickedRepository::new, false);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventStore<gen.model.test.Clicked>>(){}.type, gen.model.test.repositories.ClickedRepository::new, false);
		Seq$converter$NextConverter.configure(container);
		gen.model.Seq.Next.__setupSequenceID();
		
		container.register(gen.model.Seq.repositories.NextRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.Seq.Next>>(){}.type, gen.model.Seq.repositories.NextRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.Seq.Next>>(){}.type, gen.model.Seq.repositories.NextRepository::new, false);
		mixinReference$converter$SpecificReportConverter.configure(container);
		gen.model.mixinReference.SpecificReport.__setupSequenceID();
		
		container.register(gen.model.mixinReference.repositories.SpecificReportRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.mixinReference.SpecificReport>>(){}.type, gen.model.mixinReference.repositories.SpecificReportRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.mixinReference.SpecificReport>>(){}.type, gen.model.mixinReference.repositories.SpecificReportRepository::new, false);
		mixinReference$converter$AuthorConverter.configure(container);
		gen.model.mixinReference.Author.__setupSequenceID();
		
		container.register(gen.model.mixinReference.repositories.AuthorRepository.class);
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<gen.model.mixinReference.Author>>(){}.type, gen.model.mixinReference.repositories.AuthorRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<gen.model.mixinReference.Author>>(){}.type, gen.model.mixinReference.repositories.AuthorRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.test.LazyLoad>>(){}.type, gen.model.test.repositories.LazyLoadRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.test.SingleDetail>>(){}.type, gen.model.test.repositories.SingleDetailRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.test.Composite>>(){}.type, gen.model.test.repositories.CompositeRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.Seq.Next>>(){}.type, gen.model.Seq.repositories.NextRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.mixinReference.SpecificReport>>(){}.type, gen.model.mixinReference.repositories.SpecificReportRepository::new, false);
		
		container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<gen.model.mixinReference.Author>>(){}.type, gen.model.mixinReference.repositories.AuthorRepository::new, false);
	}
}
