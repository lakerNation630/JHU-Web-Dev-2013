package jhu.web.java.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jhu.web.java.models.Inventory;
import jhu.web.java.models.Item;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ResourceUtils;

/**
 * This Spring Bean loads the in-memory database with values upon startup of the 
 * application
 *
 */
public class DataBaseInitializer {
	private static final Logger log = Logger.getLogger(DataBaseInitializer.class);
	@PersistenceContext(name="h2")
	private EntityManager em;
	@Autowired
	private TransactionTemplate txTemplate;
	
	@PostConstruct
	public void loadDatabase(){
		txTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				BufferedReader reader = null;
				try {
					File initialItems = ResourceUtils.getFile("classpath:initial-items.txt");
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(initialItems)));
					int count = 0;
					String line = reader.readLine();
					while(StringUtils.isNotBlank(line)){
						//Skip comment lines
						if(!line.startsWith("#")){
							String[] parts = line.split(",");
							Item item = new Item();
							item.setName(parts[0]);
							item.setDescription(parts[1]);
							item.setPrice(Float.parseFloat(parts[2]));
							
							//Save to the database
							em.persist(item);
							count++;
							//Create an inventory for the item
							Inventory inventory = new Inventory();
							inventory.setAvailable(Integer.parseInt(parts[3]));
							inventory.setItem(item);
							em.persist(inventory);
						}
						
						line = reader.readLine();
					}
					log.debug(String.format("Initialized the database with [%s] items",count));
				} catch (FileNotFoundException e) {
					log.error("Can't locate the initial set of items",e);
				} catch (IOException e) {
					log.error("Can't read initialization file",e);
				}catch(NumberFormatException e){
					log.error("Bad price inside of init file",e);
				}catch(Exception e){
					log.error("Unknown Error",e);
				}finally{
					IOUtils.closeQuietly(reader);
				}
			}
		});
		
	}
}
